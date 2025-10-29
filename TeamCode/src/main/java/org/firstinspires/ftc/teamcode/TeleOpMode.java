package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// TeleOp Mode for FTC Robot
@TeleOp(name = "TeleOp Mode", group = "Linear TeleOp mode")
public class TeleOpMode extends LinearOpMode {

    // Declare hardware variables
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");

        // Set motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start
        waitForStart();

        while (opModeIsActive()) {
            // Drive control
            double drive = -gamepad1.left_stick_y; // Forward/backward
            double turn = gamepad1.right_stick_x; // Turning
            double leftPower = drive + turn;
            double rightPower = drive - turn;

            leftFrontDrive.setPower(leftPower);
            leftBackDrive.setPower(leftPower);
            rightFrontDrive.setPower(rightPower);
            rightBackDrive.setPower(rightPower);

            // Telemetry for debugging
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.update();
        }
    }
}
