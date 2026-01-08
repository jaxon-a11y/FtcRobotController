package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private DcMotor intakeMotor;

    private boolean intakeRunning = false;
    private boolean yWasPressed = false;

    public void init(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void loop(boolean yButton) {

        // Toggle on rising edge
        if (yButton && !yWasPressed) {
            intakeRunning = !intakeRunning;
        }
        yWasPressed = yButton;

        intakeMotor.setPower(intakeRunning ? 1.0 : 0.0);
    }

    // >>> THIS METHOD WAS MISSING <<<
    public boolean isRunning() {
        return intakeRunning;
    }

    // >>> REQUIRED BY telemetry <<<
    public double getPower() {
        return intakeMotor != null ? intakeMotor.getPower() : 0;
    }
}
