package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="MyAuto", group="Examples")
public class MyAuto extends LinearOpMode {

    DcMotor leftFront, rightFront, leftBack, rightBack;

    @Override
    public void runOpMode() {
        // Hardware
        leftFront  = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftBack   = hardwareMap.get(DcMotor.class, "left_back");
        rightBack  = hardwareMap.get(DcMotor.class, "right_back");

        // Reverse one side so forward = forward
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        // Repeat 4 times to return to start (a square)
        for (int i = 0; i < 4 && opModeIsActive(); i++) {

            // 1) Move forward for 2 seconds
            drive(0.5, 0.5, 0.5, 0.5, 2000);

            // 2) Turn right 90° (approx) – adjust timing as needed
            drive(0.5, -0.5, 0.5, -0.5, 800);
            // ^ 800ms is a guess — tune this for your bot

        }

        stopMotors();
    }

    // Helper method to drive with power & duration
    public void drive(double lf, double rf, double lr, double rr, long timeMs) {
        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lr);
        rightBack.setPower(rr);

        sleep(timeMs); // non-linear opmode sleep() is allowed
    }

    public void stopMotors() {
        drive(0, 0, 0, 0, 0);
    }
}
