package org.firstinspires.ftc.teamcode.robotSubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface SubSystemsInterFace {
    void init(HardwareMap hardwareMap, String name, String name2);

    void test(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry);
}
