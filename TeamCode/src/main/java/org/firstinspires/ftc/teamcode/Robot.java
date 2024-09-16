package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.vision.DriveByAprilTags.AutoDriveAprilTags;
import org.firstinspires.ftc.teamcode.OrbitUtils.Vector;
import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;
import org.firstinspires.ftc.teamcode.robotSubSystems.SubSystemManager;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainOmni.DrivetrainOmni;
import org.firstinspires.ftc.teamcode.roadRunner_1_0.messages.PoseMessage;

@Config
@TeleOp(name = "main")
public class Robot extends LinearOpMode {


    public static TelemetryPacket packet;


    @Override
    public void runOpMode() throws InterruptedException {


        FtcDashboard dashboard = FtcDashboard.getInstance();
        packet = new TelemetryPacket();

        ElapsedTime robotTime = new ElapsedTime();
        robotTime.reset();
        DrivetrainOmni.init(hardwareMap);
//        DriveTrainTank.init(hardwareMap);
        OrbitGyro.init(hardwareMap);
//        AutoDriveAprilTags.initAprilTag(hardwareMap,telemetry);
//        OrbitColorSensor.init(hardwareMap);
//        OrbitDistanceSensor.OrbitDistanceSensor(hardwareMap);
//        MagneticSensor.MagneticSensor(hardwareMap,"magneticSensor");
//        Potentiometer.Potentiometer(hardwareMap);
//        TouchSensor.TouchSensor(hardwareMap,"touchSensor");
        OrbitGyro.resetGyroStartTeleop((float) PoseMessage.heading);

        telemetry.addData("gyro",OrbitGyro.getAngle());
        telemetry.addData("lastAngle", OrbitGyro.lastAngle);
        telemetry.update();

        GlobalData.inAutonomous = false;
        GlobalData.currentTime = 0;
        GlobalData.lastTime = 0;
        GlobalData.deltaTime = 0;
        GlobalData.robotState = RobotState.TRAVEL;


        waitForStart();


        while (!isStopRequested()) {
          GlobalData.currentTime = (float) robotTime.seconds();
          Vector leftStick = new Vector(-gamepad1.left_stick_x,  gamepad1.left_stick_y);
          float omega = -gamepad1.right_trigger + gamepad1.left_trigger;
          if (gamepad1.left_bumper && SubSystemManager.wanted == RobotState.TRAVEL){
//              AutoDriveAprilTags.getAprilTagDetectionOmni();
//              AutoDriveAprilTags.getAprilTagDetectionTank();
          }else {
              DrivetrainOmni.operate(leftStick, omega, gamepad1);
//          DriveTrainTank.operate(-gamepad1.left_stick_y, gamepad1.right_trigger, gamepad1.left_trigger, telemetry, gamepad1);
          }
          SubSystemManager.setSubsystemToState(gamepad1 , gamepad2);
          GlobalData.deltaTime = GlobalData.currentTime - GlobalData.lastTime;
//        AutoDriveAprilTags.update();
          GlobalData.lastTime = GlobalData.currentTime;

          SubSystemManager.printStates(telemetry);
        }
    }


}
//dani yalechan!
// yoel yalechan!