package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive3 {

    // ============================
    // Hardware
    // ============================
    private DcMotor leftFront, leftBack, rightFront, rightBack;

    // ============================
    // Constructor
    // ============================
    public MecanumDrive3() {}

    // ============================
    // Init (NO IMU)
    // ============================
    public void init(HardwareMap hw) {

        leftFront  = hw.get(DcMotor.class, "left_front");
        leftBack   = hw.get(DcMotor.class, "left_back");
        rightFront = hw.get(DcMotor.class, "right_front");
        rightBack  = hw.get(DcMotor.class, "right_back");

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reverse left side
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
    }

    // ============================
    // Standard robot-centric drive
    // ============================
    public void drive(double power, double theta, double turn) {

        double sin = Math.sin(theta - Math.PI / 4);
        double cos = Math.cos(theta - Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double lf = (power * cos / max) + turn;
        double rf = (power * sin / max) - turn;
        double lb = (power * sin / max) + turn;
        double rb = (power * cos / max) - turn;

        // Normalize if needed
        double largest = Math.max(1.0, Math.max(Math.abs(lf),
                Math.max(Math.abs(rf), Math.max(Math.abs(lb), Math.abs(rb)))));

        lf /= largest;
        rf /= largest;
        lb /= largest;
        rb /= largest;

        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lb);
        rightBack.setPower(rb);
    }

    // ============================
    // Gamepad-style drive
    // ============================
    public void drive1(double x, double y, double turn) {
        double theta = Math.atan2(y, x);
        double r = Math.hypot(x, y);
        drive(r, theta, turn);
    }

    // ============================
    // TURN IN PLACE (new + reliable)
    // ============================
    public void turnInPlace(double power) {
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
    }

    // ============================
    // STOP ALL MOTORS
    // ============================
    public void stop() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}
