package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoForward_1_5")
public class AutoForward_1_5 extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumDrive();
        drive.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        // ===== DRIVE FORWARD FOR 1.5 SECONDS =====
        double power = 0.5; // adjust speed if needed
        double duration = 1.5; // seconds

        double startTime = getRuntime();

        while (opModeIsActive() && (getRuntime() - startTime < duration)) {
            // Drive forward (y = +power)
            drive.drive1(0, power, 0);
        }

        // STOP
        drive.drive1(0, 0, 0);

        telemetry.addData("Status", "Finished");
        telemetry.update();
        sleep(500);
    }
}
