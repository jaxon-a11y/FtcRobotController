package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "MyAuto2", group = "Auto")
public class MyAuto2 extends LinearOpMode {

    DcMotor leftFront, rightFront, leftBack, rightBack;
    IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        // -----------------------
        // Hardware map
        // -----------------------
        leftFront  = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftBack   = hardwareMap.get(DcMotor.class, "left_back");
        rightBack  = hardwareMap.get(DcMotor.class, "right_back");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        // -----------------------
        // IMU setup
        // -----------------------
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);

        telemetry.addLine("Ready. IMU Initialized.");
        telemetry.update();

        waitForStart();

        // Reset yaw to 0 at start
        imu.resetYaw();

        // -----------------------
        // Run a 4-sided square
        // -----------------------
        for (int i = 0; i < 4 && opModeIsActive(); i++) {

            // 1) Drive forward 2 seconds
            drive(0.5, 0.5, 0.5, 0.5, 500
            );

            // 2) IMU turn right 90 degrees
            turnRightIMU(80);
        }

        stopMotors();
    }

    // -----------------------------------
    // Drive with power for a time
    // -----------------------------------
    public void drive(double lf, double rf, double lr, double rr, long timeMs) {
        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftBack.setPower(lr);
        rightBack.setPower(rr);
        sleep(timeMs);
    }

    public void stopMotors() {
        drive(0, 0, 0, 0, 0);
    }

    // -----------------------------------
    // IMU Right Turn Function
    // -----------------------------------
    public void turnRightIMU(double degrees) {

        double target = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - degrees;

        // Normalize: keep angle between -180 and 180
        if (target < -180) target += 360;

        // Start turning right
        while (opModeIsActive()) {

            double current = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

            // If close to target, stop
            if (Math.abs(current - target) < 2) break;

            // Turning: right wheels backwards, left wheels forward
            leftFront.setPower(0.4);
            leftBack.setPower(0.4);
            rightFront.setPower(-0.4);
            rightBack.setPower(-0.4);
        }

        stopMotors();
        sleep(200); // small settle time
    }
}
