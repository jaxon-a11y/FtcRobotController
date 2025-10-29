package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class DriveOpMode extends OpMode {
    MecanumDrive driver = new MecanumDrive();
    Flywheel wheel = new Flywheel();
    Servo1 servo = new Servo1();

    public void init() {
        driver.init(hardwareMap);
        wheel.init(hardwareMap);
        servo.init(hardwareMap);
    }

    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        boolean input1 = gamepad1.a;
        boolean input2 = gamepad1.b;

            servo.loop(input2);
            driver.drive1(x, y, turn);
            wheel.loop(input1);
        }
    }
