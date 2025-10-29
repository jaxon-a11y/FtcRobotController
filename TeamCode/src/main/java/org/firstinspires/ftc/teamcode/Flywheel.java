package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel {
    private DcMotor flywheel;
    private boolean lastButtonState = false;
    private boolean motorOn = false;

    public void init(HardwareMap hwMap) {
        flywheel = hwMap.get(DcMotor.class,"flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void loop(boolean input1){
        if (input1 && !lastButtonState) {
            motorOn = !motorOn; // flip toggle
        }

        // Apply the toggle state to the motor
        if (motorOn) {
            flywheel.setPower(0.7
            ); // full power forward
        } else {
            flywheel.setPower(0);
        }

        // Save current button state
        lastButtonState = input1;
    }
}
