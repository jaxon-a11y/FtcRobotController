package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="PedroPath Mecanum Auto")
public class PedroPathAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");

        // Optional: reverse motors depending on wiring
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);

        MecanumFollower follower = new MecanumFollower(fl, fr, bl, br);

        PedroPath path = new PedroPath()
                .add(  0,  0,   0)
                .add( 20, 10,   0)
                .add( 40, 20,  90)
                .add( 40, 40,  90);

        waitForStart();

        // Starting pose
        double robotX = 0;
        double robotY = 0;
        double robotHeading = 0;

        for (PathPoint point : path.getPoints()) {
            while (opModeIsActive()) {
                // robotX = ...
                // robotY = ...
                // robotHeading = ...

                if (follower.followPoint(robotX, robotY, robotHeading, point))
                    break;

                telemetry.addData("Target", "(%.1f, %.1f)", point.x, point.y);
                telemetry.update();
            }
        }
    }
}
