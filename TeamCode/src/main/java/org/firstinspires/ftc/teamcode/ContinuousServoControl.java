package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="ContinuousServoControl", group="Examples")
public class ContinuousServoControl extends LinearOpMode {

    // Declare servo object
    private CRServo continuousServoL, continuousServoR;

    private static final double LOW_POWER = 0.3; // 30% of max power
    private static final double SPIN_DURATION = 0.35;


    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize servo, use configured name in the robot configuration on the Driver Station
        continuousServoR = hardwareMap.get(CRServo.class, "right_middle");
        continuousServoL = hardwareMap.get(CRServo.class, "left_middle");
        continuousServoL.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Info", "LOW_POWER = " + LOW_POWER + ", SPIN_DURATION = " + SPIN_DURATION + "s");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            // Control continuous servo based on gamepad input
            if (gamepad1.b) {
                // Rotate servo forward at full speed
                continuousServoL.setPower(1.0);
                continuousServoL.setPower(LOW_POWER);
                continuousServoR.setPower(1.0);
                continuousServoR.setPower(LOW_POWER);
            } else {
                // Stop the servo
                continuousServoL.setPower(0.0);
                continuousServoL.setPower(0.0);
            }
        }
    }
}