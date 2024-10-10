package org.firstinspires.ftc.teamcode.robotSubSystems.Telescope;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OrbitUtils.MathFuncs;
import org.firstinspires.ftc.teamcode.OrbitUtils.PID;
import org.firstinspires.ftc.teamcode.robotSubSystems.arm.ArmConstants;

public class   Telescope {
    public static DcMotor telMotor;
    public static float currentPose = 0;
    public static float wantedPose = 0;
    public static float zeroPose = 0;
    public static final PID telescopePID = new PID(TelescopeConstants.telKp, TelescopeConstants.telKi, TelescopeConstants.telKd, TelescopeConstants.telKf, TelescopeConstants.telIzone);

    public static void init(HardwareMap hardwareMap , String name) {
        telMotor = hardwareMap.get(DcMotor.class, name);
        telMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public static void operate(TelescopeStates state , Gamepad gamepad1 , Gamepad gamepad2){
        switch (state){
            case TRAVEL:
                wantedPose = TelescopeConstants.travelPose;
                break;
            case INTAKE:
                wantedPose = TelescopeConstants.intakePose;
                break;
            case LOWBASKET:
                wantedPose = TelescopeConstants.lowBasket;
                break;
            case HIGHBASKET:
                wantedPose = TelescopeConstants.highBasket;
                break;
            case LOWCHAMBER:
                wantedPose = TelescopeConstants.lowChamber;
                break;
            case HIGHCHAMBER:
                wantedPose = TelescopeConstants.highChamber;
                break;
            case OVERRIDE:
                wantedPose += -gamepad1.right_stick_y * TelescopeConstants.overrideFactor;
                break;
            case INTAKEOVERRIDE:
                wantedPose = (float) (-gamepad1.left_stick_x/Math.cos(Math.atan(ArmConstants.armHFromTheGround/-gamepad1.right_stick_x)));
                break;

        }
        currentPose = telMotor.getCurrentPosition() - zeroPose;
        telescopePID.setWanted(wantedPose);
        telMotor.setPower(telescopePID.update(currentPose));
        if(gamepad2.right_bumper) {
            zeroPose = telMotor.getCurrentPosition();
        }
    }
    public static void test(Gamepad gamepad1, boolean auto, Telemetry telemetry){
        if (!auto) {
            wantedPose += -gamepad1.right_stick_y * TelescopeConstants.overrideFactor;
        }else {
            wantedPose = TelescopeConstants.testPose;
        }
        currentPose = telMotor.getCurrentPosition();
        telescopePID.setWanted(wantedPose);
        telMotor.setPower(telescopePID.update(currentPose));

        telemetry.addData("wanted pos", wantedPose);
        telemetry.addData("pose", currentPose);
        telemetry.addData("power", telMotor.getPower());
        telemetry.update();
    }
}
