package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import org.firstinspires.ftc.teamcode.Flywheel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp
public class ServoOpMode extends OpMode {
    Servo1 servo = new Servo1();

    @Override
    public void init(){
        servo.init(hardwareMap);
    }

    @Override
    public void loop (){
        boolean input1 = gamepad1.a;
        servo.loop(input1);
    }

}
