package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake2 {

    // Two separate intake motors
    private DcMotor intakeY;   // Y button (toggle)
    private DcMotor intakeRB;  // RB button (hold)

    // Toggle for Y-button intake
    private final Toggle yToggle = new Toggle();

    // Constants
    private static final double INTAKE_POWER = 0.8;

    public Intake2() {}

    public void init(HardwareMap hw) {
        intakeY = hw.get(DcMotor.class, "intakeY");
        intakeRB = hw.get(DcMotor.class, "intakeRB");

        intakeY.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRB.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeY.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeRB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Call once per loop from TeleOp
     *
     * @param yButton  gamepad1.y (toggle intake)
     * @param rbButton gamepad1.right_bumper (hold intake)
     */
    public void loop(boolean yButton, boolean rbButton) {

        // ---- Y BUTTON (TOGGLE) ----
        boolean yRunning = yToggle.update(yButton);
        intakeY.setPower(yRunning ? INTAKE_POWER : 0.0);

        // ---- RB BUTTON (HOLD) ----
        intakeRB.setPower(rbButton ? INTAKE_POWER : 0.0);
    }

    // Optional helpers
    public boolean isYIntakeRunning() {
        return yToggle.get();
    }

    public void stopAll() {
        intakeY.setPower(0);
        intakeRB.setPower(0);
    }
    public void setPower(double power) {
        intakeY.setPower(power);
        intakeRB.setPower(power);
    }
}
