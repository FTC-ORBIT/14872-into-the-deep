package org.firstinspires.ftc.teamcode.robotSubSystems.forbar;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Fourbar {
    public static Servo servo;
    public static Servo servo2;
    public static float pos;

    public static boolean leftBumper = false;
    public static boolean rightBumper = false;
    public static boolean leftDPade = false;
    public static boolean rightDPade = false;



    public static void init(HardwareMap hardwareMap , String name , String name2){
        servo = hardwareMap.get(Servo.class, name);
        servo2 = hardwareMap.get(Servo.class, name2);
    }
    public static void operate(FourbarStates state){
        switch (state){
            case DROP:
                pos = FourbarConstants.dropPose;
                break;
            case COLLECT:
                pos = FourbarConstants.collectPos;
                break;
            case STOP:
            default:
                pos = FourbarConstants.stopPos;
            break;
        }
        servo.setPosition(pos);
        servo2.setPosition(pos);

    }
    public static void  test(Gamepad gamepad1 , Telemetry telemetry, boolean eachServo, boolean bothServos){
        if (!leftBumper && gamepad1.left_bumper){
            pos += 0.05f;
        } else if (!rightBumper && gamepad1.right_bumper){
            pos -= 0.05f;
        } else if (!leftDPade && gamepad1.dpad_left) {
            pos += 0.01f;
        } else if (!rightDPade && gamepad1.dpad_right) {
            pos -= 0.01f;
        }

        if (eachServo && !bothServos){
            servo.setPosition(pos);
        }else if (!bothServos){
            servo2.setPosition(pos);
        }else {
            servo.setPosition(pos);
            servo2.setPosition(pos);
        }
        leftBumper = gamepad1.left_bumper;
        rightBumper = gamepad1.right_bumper;
        leftDPade = gamepad1.dpad_left;
        rightDPade = gamepad1.dpad_right;

        telemetry.addData("speed - or - pose", pos);
        telemetry.update();
    }
}
