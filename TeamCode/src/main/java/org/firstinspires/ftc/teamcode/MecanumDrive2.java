package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class MecanumDrive2 {

    // ============================
    // Hardware
    // ============================
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

    // ============================
    // Constructor
    // ============================
    public MecanumDrive2() {}

    // ============================
    // Init
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

        // IMU initialization
        imu = hw.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );

        imu.initialize(new IMU.Parameters(orientation));
    }

    // ============================
    // Robot-Centric (standard) Drive
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
    // Gamepad stick input
    // ============================
    public void drive1(double x, double y, double turn) {
        double theta = Math.atan2(y, x);
        double r = Math.hypot(x, y);
        drive(r, theta, turn);
    }

    // ============================
    // OPTIONAL: Field-Centric Drive
    // (Call this instead of drive1 if needed)
    // ============================
    public void driveFieldCentric(double x, double y, double turn) {
        double heading = imu.getRobotYawPitchRollAngles().getYaw();

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double theta = Math.atan2(rotY, rotX);
        double r = Math.hypot(rotX, rotY);

        drive(r, theta, turn);
    }
}
