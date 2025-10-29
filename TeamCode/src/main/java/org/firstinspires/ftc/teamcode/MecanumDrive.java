package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumDrive {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    private Servo rightMiddle;
    private Servo leftMiddle;
    private IMU imu;

    public void init(HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotor.class,"left_front");
        leftBack = hwMap.get(DcMotor.class,"left_back");
        rightFront = hwMap.get(DcMotor.class,"right_front");
        rightBack = hwMap.get(DcMotor.class,"right_back");
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );
        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double power, double theta, double turn){
        double sin = Math.sin (theta - Math.PI/4);
        double cos = Math.cos (theta - Math.PI/4);
        double max = Math.max (Math.abs(sin), Math.abs (cos));

        double leftFrontPower = power * cos/max + turn;
        double rightFrontPower = power * sin/max - turn;
        double leftBackPower = power * sin/max + turn;
        double rightBackPower = power * cos/max - turn;

        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + turn;
            rightFrontPower /= power + turn;
            leftBackPower /= power + turn;
            rightBackPower /= power + turn;
        }
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }
    public void drive1 (double x,double y, double turn){
        double theta = Math.atan2(y,x);
        double r = Math.hypot(x,y);

        this.drive(r,theta,turn);
    }
}