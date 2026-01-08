package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BeltDrive {

    private CRServo left, right;

    private static final double POWER = 0.3;

    public void init(HardwareMap hw) {
        left = hw.get(CRServo.class, "left_middle");
        right = hw.get(CRServo.class, "right_middle");

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void run() {
        left.setPower(POWER);
        right.setPower(POWER);
    }

    public void stop() {
        left.setPower(0);
        right.setPower(0);
    }
}
