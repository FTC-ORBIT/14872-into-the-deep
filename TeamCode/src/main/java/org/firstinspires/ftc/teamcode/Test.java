package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OrbitUtils.MathFuncs;
import org.firstinspires.ftc.teamcode.roadRunner_1_0.messages.PoseMessage;
import org.firstinspires.ftc.teamcode.vision.DriveByAprilTags.AutoDriveAprilTags;

import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;
import org.firstinspires.ftc.teamcode.robotSubSystems.SubSystemManager;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainOmni.DrivetrainOmni;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainTank.DriveTrainTank;

@Config
@TeleOp(name = "test")
public class Test extends LinearOpMode {
public static int x = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        TelemetryPacket packet  = new TelemetryPacket();

        ElapsedTime robotTime = new ElapsedTime();
        robotTime.reset();
//        DrivetrainOmni.init(hardwareMap);
//        OrbitGyro.init(hardwareMap);
//        AutoDriveAprilTags.initAprilTag(hardwareMap, telemetry);


        OrbitGyro.resetGyroStartTeleop((float) PoseMessage.heading);
        telemetry.addData("gyro", OrbitGyro.getAngle());
        telemetry.addData("lastAngle", OrbitGyro.lastAngle);
        telemetry.update();

        GlobalData.inAutonomous = false;
        GlobalData.currentTime = 0;
        GlobalData.lastTime = 0;
        GlobalData.deltaTime = 0;
        GlobalData.robotState = RobotState.TRAVEL;
        GlobalData.hasGamePiece = false;


        waitForStart();

//        DriveTrainTank.init(hardwareMap);

        while (!isStopRequested()) {
           dashboard.sendTelemetryPacket(packet);
        }
    }
}
//dani yalechan!
// yoel yalechan!