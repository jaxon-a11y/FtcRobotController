package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable
public class Auton extends OpMode {

    /* ================= HARDWARE ================= */
    private DcMotorEx flywheel;
    private Intake2 intake;
    private CRServo leftServo;
    private CRServo rightServo;

    /* ================= TIMING ================= */
    private ElapsedTime stateTimer = new ElapsedTime();
    public static double SHOOT_TIME = 3.0; // seconds (tune this)
    public static double PATH4_PRESHOOT_DELAY = 1.0; // seconds after Path 4 starts


    /* ================= PATHING ================= */
    private TelemetryManager panelsTelemetry;
    public Follower follower;
    private int pathState = 0;
    private Paths paths;

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(85.268, 9.366, Math.toRadians(90)));

        paths = new Paths(follower);

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        intake = new Intake2();
        intake.init(hardwareMap);
        leftServo = hardwareMap.get(CRServo.class, "left_middle");
        rightServo = hardwareMap.get(CRServo.class, "right_middle");

        // Servo directions
        leftServo.setDirection(CRServo.Direction.REVERSE);
        rightServo.setDirection(CRServo.Direction.FORWARD);

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void start() {
        follower.followPath(paths.Path1);
        stateTimer.reset();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

    /* ================= PATH DEFINITIONS ================= */
    public static class Paths {

        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;

        public Paths(Follower follower) {
            Path1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(85.268, 9.366), new Pose(85.268, 86.244))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(47))
                    .build();

            Path2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(85.268, 86.244), new Pose(100.000, 83.902))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(47), Math.toRadians(0))
                    .build();

            Path3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(100.000, 83.902), new Pose(115.500, 83.902))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            Path4 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(115.500, 83.902), new Pose(85.268, 86.244))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(47))
                    .build();
        }
    }

    /* ================= AUTON STATE MACHINE ================= */
    public void autonomousPathUpdate() {
        switch (pathState) {

            /* ================= PATH 1 ================= */
            case 0:
                // Follow Path 1
                if (!follower.isBusy()) {
                    follower.followPath(paths.Path1);
                    pathState = 1;
                    stateTimer.reset();
                }
                break;

            case 1:
                if (stateTimer.seconds() > 1.5) {
                    flywheel.setPower(0.8);
                }
                if (stateTimer.seconds() > 3.0) {
                    intake.setPower(0.7);
                    leftServo.setPower(0.8);
                    rightServo.setPower(0.8);
                }
                if (stateTimer.seconds() > 4.5) {
                    flywheel.setPower(0);
                    intake.stopAll();
                    leftServo.setPower(0);
                    rightServo.setPower(0);

                    follower.followPath(paths.Path2);
                    pathState = 2;
                    stateTimer.reset();
                }
                break;


            /* ================= PATH 2 ================= */
            case 2:
                flywheel.setPower(0);
                intake.stopAll();
                leftServo.setPower(0);
                rightServo.setPower(0);

                if (!follower.isBusy() && stateTimer.seconds() == 0) {
                    stateTimer.reset();
                }

                if (!follower.isBusy() && stateTimer.seconds() > 2.0) {
                    follower.followPath(paths.Path3);
                    pathState = 3;
                    stateTimer.reset();
                }
                break;


            /* ================= PATH 3 ================= */
            case 3:
                // Only intake runs
                intake.setPower(0.7);

                if (!follower.isBusy()) {
                    follower.followPath(paths.Path4);
                    pathState = 4;
                    stateTimer.reset();
                }
                break;

            /* ================= PATH 4 ================= */
            /* ================= PATH 4 (DRIVE ONLY) ================= */
            case 4:
                // Drive Path 4 ONLY — no shooting yet
                intake.stopAll();
                flywheel.setPower(0);
                leftServo.setPower(0);
                rightServo.setPower(0);

                if (stateTimer.seconds() > PATH4_PRESHOOT_DELAY) {
                    flywheel.setPower(0.85);
                } else {
                    flywheel.setPower(0);
                }

                if (!follower.isBusy()) {
                    // Path 4 finished → start flywheel spin-up
                    pathState = 5;
                    stateTimer.reset();
                }
                break;
            case 5:
                flywheel.setPower(0.85);

                // Give flywheel time to reach speed
                if (stateTimer.seconds() > 0.4) {
                    pathState = 6;
                    stateTimer.reset();
                }
                break;
            /* ================= PATH 4 SHOOT ================= */
            case 6:
                flywheel.setPower(0.9);
                intake.setPower(0.7);
                leftServo.setPower(0.6);
                rightServo.setPower(0.6);

                if (stateTimer.seconds() > 2.6) {
                    flywheel.setPower(0);
                    intake.stopAll();
                    leftServo.setPower(0);
                    rightServo.setPower(0);

                    pathState = 7;
                }
                break;
            case 7:
                // Done
                break;
        }
    }
}