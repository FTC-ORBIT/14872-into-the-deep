package org.firstinspires.ftc.teamcode.robotSubSystems;

import static org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainOmni.DrivetrainOmni.motors;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotSubSystems.Telescope.Telescope;
import org.firstinspires.ftc.teamcode.robotSubSystems.Telescope.TelescopeStates;
import org.firstinspires.ftc.teamcode.robotSubSystems.arm.Arm;
import org.firstinspires.ftc.teamcode.robotSubSystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.robotSubSystems.forbar.Fourbar;
import org.firstinspires.ftc.teamcode.robotSubSystems.forbar.FourbarStates;
import org.firstinspires.ftc.teamcode.vision.DriveByAprilTags.AutoDriveAprilTags;
import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class SubSystemManager {

    public static RobotState lastState = RobotState.TRAVEL;

    public static RobotState wanted = RobotState.TRAVEL;
    public static FourbarStates fourbarState = FourbarStates.STOP;
    public static ArmState armState = ArmState.TRAVEL;
    public static TelescopeStates telescopeState = TelescopeStates.TRAVEL;


    public static boolean telescopeOverride = false;
    public static boolean armOverride = false;


    private static RobotState getState(Gamepad gamepad) {

        if (gamepad.x || gamepad.y){
            telescopeOverride  = false;
            armOverride = false;
        }


        return gamepad.b ? RobotState.TRAVEL
                : gamepad.a ? RobotState.INTAKE
                : !gamepad.left_bumper & gamepad.x ? RobotState.LOWBASKET
                : !gamepad.left_bumper & gamepad.y ? RobotState.HIGHBASKET
                : gamepad.left_bumper & gamepad.x ? RobotState.LOWCHAMBER
                : gamepad.left_bumper & gamepad.y ? RobotState.HIGHCHAMBER
                : gamepad.right_bumper ? RobotState.DEPLETE
                : gamepad.back ? RobotState.CLIMB
               : lastState;
    }

    private static RobotState getStateFromWantedAndCurrent(RobotState stateFromDriver) {

        switch (stateFromDriver) {
            case INTAKE:
                break;
            case TRAVEL:
                break;

        }
        return stateFromDriver;
    }

    public static void setSubsystemToState(Gamepad gamepad1, Gamepad gamepad2) {
//        final RobotState wanted = getStateFromWantedAndCurrent(getState(gamepad1));
        wanted = getState(gamepad1);




        switch (wanted) {
            case TRAVEL:
                fourbarState = FourbarStates.STOP;
               if (!telescopeOverride) {
                   telescopeState = TelescopeStates.TRAVEL;
               }
               if (!armOverride){
                   armState = ArmState.TRAVEL;
               }
                break;
            case INTAKE:
                fourbarState = FourbarStates.COLLECT;
                if (!telescopeOverride){
                    telescopeState = TelescopeStates.INTAKE;
                }
                if (!armOverride){
                    armState = ArmState.INTAKE;
                }
                break;
            case DEPLETE:
                fourbarState = FourbarStates.DROP;
                break;
            case HIGHCHAMBER:
                fourbarState = FourbarStates.STOP;
                if (!telescopeOverride){
                    telescopeState = TelescopeStates.HIGHCHAMBER;
                }
               if (!armOverride){
                   armState = ArmState.HIGHBASKET;
               }
                break;
            case LOWCHAMBER:
                fourbarState = FourbarStates.STOP;
               if (!telescopeOverride){
                   telescopeState = TelescopeStates.LOWCHAMBER;
               }
                if (!armOverride){
                    armState = ArmState.LOWCHAMBER;
                }
                break;
            case LOWBASKET:
                fourbarState = FourbarStates.STOP;
                if (!telescopeOverride){
                    telescopeState = TelescopeStates.LOWBASKET;
                }
                if (!armOverride){
                    armState = ArmState.LOWBASKET;
                }
                break;
            case HIGHBASKET:
                fourbarState = FourbarStates.STOP;
                if (!telescopeOverride){
                    telescopeState = TelescopeStates.HIGHBASKET;
                }
                armState = ArmState.HIGHBASKET;
                break;
            case CLIMB:
                fourbarState = FourbarStates.STOP;
                if (!telescopeOverride){
                    telescopeState = TelescopeStates.TRAVEL;
                }
                if (!armOverride){
                    armState = ArmState.CLIMB;
                }
                break;
        }

        lastState = wanted;


        if (gamepad1.right_stick_y != 0 && lastState != RobotState.INTAKE){
            telescopeOverride = true;
            telescopeState = TelescopeStates.OVERRIDE;
        } else if (gamepad1.right_stick_y != 0) {
            telescopeOverride = true;
            telescopeState = TelescopeStates.INTAKEOVERRIDE;

            armOverride = true;
            armState = ArmState.OVERRIDE;
        }

        Fourbar.operate(fourbarState);
        Arm.operate(armState, gamepad1);
        Telescope.operate(telescopeState, gamepad1, gamepad2);



        if (gamepad1.dpad_down) OrbitGyro.resetGyro();
    }

    public static void printStates(Telemetry telemetry) {
        telemetry.addData("Robot current state ", SubSystemManager.wanted);
        telemetry.addData("Robot last state", SubSystemManager.lastState);
        if (AutoDriveAprilTags.targetFound){
            telemetry.addData("tag has found",AutoDriveAprilTags.desiredTag.id);
        }else if (AutoDriveAprilTags.skippingTagTelemetry){
            telemetry.addData("other tag ha found, skipping",AutoDriveAprilTags.desiredTag.id);
        }else {
            telemetry.addLine("tag not in the lib");
        }
//        telemetry.addData("X",PoseStorage.currentPose.getX());
//        telemetry.addData("Y",PoseStorage.currentPose.getY());
        telemetry.addData("gyro", OrbitGyro.getAngle());
        telemetry.addData("lastAngle", OrbitGyro.lastAngle);
        telemetry.addData("currentTime", GlobalData.currentTime);
        telemetry.addData("lastTime", GlobalData.lastTime);
        telemetry.addData("deltaTime",GlobalData.deltaTime);
        telemetry.addData("lf power" , motors[0].getPower());
        telemetry.addData("rf power" , motors[1].getPower());
        telemetry.addData("lb power" , motors[2].getPower());
        telemetry.addData("rb power" , motors[3].getPower());
//        telemetry.addData("distance in inch", OrbitDistanceSensor.getDistance());
//        telemetry.addData("color", OrbitColorSensor.hasGamePiece());
//        telemetry.addData("magnetic press?", MagneticSensor.getState());
//        telemetry.addData("touchSensor press?", TouchSensor.getState());
//        telemetry.addData("potentiometer", Potentiometer.getVolt());
        telemetry.update();
    }
}

//dani yalechan!
// yoel yalechan!
// עומר היה פה