package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake3 {

    private DcMotor intakeMotor;
    private boolean running = false;
    private boolean lastTogglePressed = false;

    private double currentPower = 0.0;

    // Power levels
    private static final double NORMAL_POWER = 1.0;
    private static final double SLOW_DRIVE_POWER = 0.7;
    private static final double FLYWHEEL_RUNNING_POWER = 0.8;

    public void init(HardwareMap hwMap) {
        intakeMotor = hwMap.get(DcMotor.class, "intake");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Toggle intake ON/OFF with a button press */
    public void toggleRun(boolean buttonPressed) {
        if (buttonPressed && !lastTogglePressed) {
            running = !running;
        }
        lastTogglePressed = buttonPressed;
    }

    /**
     * Update intake behavior.
     * @param drivingFast  true if robot is moving quickly
     * @param flywheelOn   true if shooter is active
     */
    public void update(boolean drivingFast, boolean flywheelOn) {

        if (!running) {
            currentPower = 0;
            intakeMotor.setPower(0);
            return;
        }

        // Intake is running — adjust power automatically
        if (flywheelOn) {
            currentPower = FLYWHEEL_RUNNING_POWER;
        } else if (drivingFast) {
            currentPower = SLOW_DRIVE_POWER;
        } else {
            currentPower = NORMAL_POWER;
        }

        intakeMotor.setPower(currentPower);
    }

    // ===== Getter functions for telemetry =====
    public boolean isRunning() {
        return running;
    }

    public double getPower() {
        return currentPower;
    }
}
