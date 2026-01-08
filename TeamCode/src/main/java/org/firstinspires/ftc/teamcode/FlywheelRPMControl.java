package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Flywheel RPM Controlled", group="Shooter")
public class FlywheelRPMControl extends OpMode {

    private DcMotorEx flywheel;

    // Start RPM
    private double targetRPM = 3000;

    private double increment = 500;  // RPM change per button press
    private boolean dpadUpLast = false;
    private boolean dpadDownLast = false;

    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Optional: tune PIDF (default works okay)
//        flywheel.setVelocityPIDFCoefficients(50, 0, 0, 12);

        telemetry.addLine("RPM Control Ready");
    }

    @Override
    public void loop() {

        // ========= RPM Increase =========
        if (gamepad1.dpad_up && !dpadUpLast) {
            targetRPM += increment;
        }

        // ========= RPM Decrease =========
        if (gamepad1.dpad_down && !dpadDownLast) {
            targetRPM -= increment;
        }

        // Clamp RPM to safe range
        targetRPM = Math.max(0, Math.min(targetRPM, 6000));

        // Convert RPM → ticks/second for DcMotorEx velocity
        double ticksPerRev = 28;  // change if your motor uses a different encoder
        double ticksPerSecond = (targetRPM / 60.0) * ticksPerRev;

        // APPLY VELOCITY CONTROL
        flywheel.setVelocity(ticksPerSecond);

        dpadUpLast = gamepad1.dpad_up;
        dpadDownLast = gamepad1.dpad_down;

        // ===== Telemetry =====
        telemetry.addData("Target RPM", targetRPM);
        telemetry.addData("Actual RPM", flywheel.getVelocity() / ticksPerRev * 60.0);
        telemetry.addData("Ticks/sec", flywheel.getVelocity());
        telemetry.addLine("Use D-pad UP/DOWN to adjust RPM");
        telemetry.update();
    }
}
