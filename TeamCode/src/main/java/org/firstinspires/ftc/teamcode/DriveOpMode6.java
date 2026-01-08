package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "DriveOpMode6", group = "TeleOp")
public class DriveOpMode6 extends OpMode {

    // ============================
    // Subsystems
    // ============================
    private MecanumDrive2 drive;
    private Intake2 intake;
    private Servo2 kicker;
    private BeltDrive belts;

    private DcMotorEx flywheel;

    // ============================
    // Flywheel variables
    // ============================
    private static final double MAX_RPM = 5000;
    private static final double MIN_RPM = 0;
    private static final double RPM_INCREMENT = 500;
    private static final double TICKS_PER_REV = 28;

    private double targetRPM = 5000;
    private final Toggle flywheelToggle = new Toggle();

    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    @Override
    public void init() {

        drive = new MecanumDrive2();
        intake = new Intake2();
        kicker = new Servo2();
        belts = new BeltDrive();

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        kicker.init(hardwareMap);
        belts.init(hardwareMap);

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {

        // ============================
        // DRIVE (with precision mode)
        // ============================
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        double slowMultiplier = gamepad1.left_bumper ? 0.35 : 1.0;

        drive.drive1(
                x * slowMultiplier,
                y * slowMultiplier,
                turn * slowMultiplier
        );

        // ============================
        // KICKER
        // ============================
        kicker.loop(gamepad1.b);

        // ============================
        // BELTS (hold B)
        // ============================
        if (gamepad1.b) belts.run();
        else            belts.stop();

        // ============================
        // INTAKES
        // Y  → toggle intake
        // RB → hold intake
        // ============================
        intake.loop(gamepad1.y, gamepad1.right_bumper);

        // ============================
        // FLYWHEEL (toggle with A)
        // ============================
        boolean flywheelOn = flywheelToggle.update(gamepad1.a);

        if (gamepad1.dpad_up && !lastDpadUp)   targetRPM += RPM_INCREMENT;
        if (gamepad1.dpad_down && !lastDpadDown) targetRPM -= RPM_INCREMENT;

        targetRPM = clamp(targetRPM, MIN_RPM, MAX_RPM);

        lastDpadUp = gamepad1.dpad_up;
        lastDpadDown = gamepad1.dpad_down;

        double ticksPerSecond = (targetRPM / 60.0) * TICKS_PER_REV;
        flywheel.setVelocity(flywheelOn ? ticksPerSecond : 0);

        // ============================
        // TELEMETRY
        // ============================
        telemetry.addLine("=== DriveOpMode6 ===");

        telemetry.addLine("\n--- Drive ---");
        telemetry.addData("Precision Mode", gamepad1.left_bumper ? "ON" : "OFF");

        telemetry.addLine("\n--- Flywheel ---");
        telemetry.addData("Target RPM", targetRPM);
        telemetry.addData("Current Velocity", flywheel.getVelocity());
        telemetry.addData("State", flywheelOn ? "ON" : "OFF");

        telemetry.addLine("\n--- Intakes ---");
        telemetry.addData("Y Intake (Toggle)", intake.isYIntakeRunning());
        telemetry.addData("RB Intake (Hold)", gamepad1.right_bumper);

        telemetry.addLine("\n--- Belts ---");
        telemetry.addData("Active", gamepad1.b);

        telemetry.addLine("\n--- Kicker ---");
        telemetry.addData("Spinning", kicker.isSpinning());

        telemetry.update();
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
