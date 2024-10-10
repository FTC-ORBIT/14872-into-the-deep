package org.firstinspires.ftc.teamcode.robotSubSystems.arm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OrbitUtils.PID;

public class Arm {
public static DcMotor armMotor;
public static DcMotor armMotor2;
public static Servo servo;
public static float currentPos = 0f;
public static float currentPos2 = 0f;
public static float wantedPos = 0f;
public static float servoPos;
public static boolean leftBumper;
public static boolean rightBumper;
public static boolean leftDPade;
public static boolean rightDPade;
public static PID armPID = new PID(ArmConstants.ArmKP , ArmConstants.ArmKI , ArmConstants.ArmKD , ArmConstants.ArmKF , ArmConstants.ArmIZone);
public static void init(HardwareMap hardwaremap , String name , String name2){
    armMotor = hardwaremap.get(DcMotor.class , name);
    armMotor2 = hardwaremap.get(DcMotor.class , name2);
    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
}
public static void operate(ArmState state, Gamepad gamepad1){
    switch (state){
        case TRAVEL:
            wantedPos = ArmConstants.travelPos;
            servoPos = ArmConstants.servoTravelPos;
            break;
        case INTAKE:
            wantedPos = ArmConstants.intakePos;
            servoPos = ArmConstants.servoIntakePos;
            break;
        case HIGHBASKET:
            wantedPos = ArmConstants.highBasketPos;
            servoPos = ArmConstants.servoBasketPos;
            break;
        case LOWBASKET:
            wantedPos = ArmConstants.lowBasketPos;
            servoPos = ArmConstants.servoBasketPos;
            break;
        case HIGHCHAMBER:
            wantedPos = ArmConstants.highChamberPos;
            servoPos = ArmConstants.servoChamberPos;
            break;
        case LOWCHAMBER:
            wantedPos = ArmConstants.lowChamberPos;
            servoPos = ArmConstants.servoChamberPos;
            break;
        case OVERRIDE:
            wantedPos = ArmConstants.overrideFactor * -gamepad1.right_stick_y;
            break;
    }
    armPID.setWanted(wantedPos);
    servo.setPosition(servoPos);
    currentPos = armMotor.getCurrentPosition();
    currentPos2 = armMotor2.getCurrentPosition();

    armMotor.setPower(armPID.update(currentPos));
    armMotor2.setPower(armPID.update(currentPos2));
}
public static void test(Gamepad gamepad1 , Telemetry telemetry, boolean auto){
    if(auto){
        wantedPos = ArmConstants.testPos;
    }else{
        wantedPos = ArmConstants.overrideFactor * -gamepad1.right_stick_y;
    }
    armPID.setWanted(wantedPos);
    currentPos = armMotor.getCurrentPosition();
    currentPos2 = armMotor2.getCurrentPosition();

    armMotor.setPower(armPID.update(currentPos));
    armMotor2.setPower(armPID.update(currentPos2));


    telemetry.addData("pos" , currentPos);
    telemetry.addData("pos2" , currentPos2);
    telemetry.update();
    }
    public static void servoTest(Gamepad gamepad1 , Telemetry telemetry){
    if (!leftBumper && gamepad1.left_bumper){
        servoPos += 0.5;
    } else if (!rightBumper && gamepad1.right_bumper){
        servoPos -= 0.5;
    } else if (!leftDPade && gamepad1.dpad_left){
        servoPos += 0.1;
    } else if (!rightDPade && gamepad1.dpad_right){
        servoPos -= 0.1;
    }
    leftBumper = gamepad1.left_bumper;
    rightBumper = gamepad1.right_bumper;
    leftDPade = gamepad1.dpad_left;
    rightDPade = gamepad1.dpad_right;
    servo.setPosition(servoPos);
    telemetry.addData( "servoPos" , servoPos);
    telemetry.update();
    }

}