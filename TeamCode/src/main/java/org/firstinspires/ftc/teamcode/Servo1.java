package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Servo1 {
    private CRServo RightServo;
    private CRServo LeftServo;
    private boolean buttonPressedLast = false;
    private boolean isSpinning = false;
    private ElapsedTime timer = new ElapsedTime();
    private double spinDuration = 0.35;


    public void init(HardwareMap hwMap) {
        RightServo = hwMap.get(CRServo.class,"right_middle");
        LeftServo = hwMap.get(CRServo.class,"left_middle");
        RightServo.setDirection(CRServo.Direction.REVERSE);
    }
    public void loop(boolean input2){

        if (input2 && !buttonPressedLast && !isSpinning) {
            isSpinning = true;
            timer.reset();
            LeftServo.setPower(1.0);
            RightServo.setPower(1.0);
        }

        // Stop servo after time elapses
        if (isSpinning && timer.seconds() > spinDuration) {
            LeftServo.setPower(0);
            RightServo.setPower(0);
            isSpinning = false;
        }

        // Remember button state for next loop
        buttonPressedLast = input2;
    }
}
