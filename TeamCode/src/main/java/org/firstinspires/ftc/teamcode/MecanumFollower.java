package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MecanumFollower {

    // Motors
    DcMotor fl, fr, bl, br;

    // Tuning constants
    public double kStrafe  = 0.03;
    public double kForward = 0.03;
    public double kTurn    = 0.015;

    public MecanumFollower(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
    }
    public boolean followPoint(
            double robotX, double robotY, double robotHeading,
            PathPoint target) {

        double dx = target.x - robotX;
        double dy = target.y - robotY;

        // Distance check
        double distance = Math.hypot(dx, dy);

        // Angle from robot to target
        double absoluteAngleToPoint = Math.toDegrees(Math.atan2(dy, dx));
        double relativeAngle = angleWrap(absoluteAngleToPoint - robotHeading);

        // Decompose movement into robot-centric vectors
        double forwardPower = Math.cos(Math.toRadians(relativeAngle)) * distance * kForward;
        double strafePower  = Math.sin(Math.toRadians(relativeAngle)) * distance * kStrafe;

        // Heading control
        double turnError = angleWrap(target.heading - robotHeading);
        double turnPower = turnError * kTurn;

        // Clip the powers
        forwardPower = clip(forwardPower, -0.75, 0.75);
        strafePower  = clip(strafePower,  -0.75, 0.75);
        turnPower    = clip(turnPower,    -0.5,  0.5);

        // Holonomic mecanum drive equations
        double flp = forwardPower + strafePower + turnPower;
        double frp = forwardPower - strafePower - turnPower;
        double blp = forwardPower - strafePower + turnPower;
        double brp = forwardPower + strafePower - turnPower;

        fl.setPower(flp);
        fr.setPower(frp);
        bl.setPower(blp);
        br.setPower(brp);

        return distance < 1.5 && Math.abs(turnError) < 4;
    }

    private double clip(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    private double angleWrap(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
