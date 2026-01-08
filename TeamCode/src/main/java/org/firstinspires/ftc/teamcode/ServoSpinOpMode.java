package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ServoSpinOpMode extends LinearOpMode {

    private CRServo continuousServoL, continuousServoR;

    private static final double LOW_POWER = 0.3;      // 30% power
    private static final double SPIN_DURATION = 0.35; // seconds

    @Override
    public void runOpMode() throws InterruptedException {

        continuousServoR = hardwareMap.get(CRServo.class, "right_middle");
        continuousServoL = hardwareMap.get(CRServo.class, "left_middle");

        // Reverse L servo so both move the same direction
        continuousServoR.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Info", "LOW_POWER = " + LOW_POWER +
                ", SPIN_DURATION = " + SPIN_DURATION + "s");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.b) {
                continuousServoL.setPower(LOW_POWER);
                continuousServoR.setPower(LOW_POWER);
            } else {
                continuousServoL.setPower(0);
                continuousServoR.setPower(0);
            }


            telemetry.addData("Servos", "Power set based on B button");
            telemetry.update();
        }
    }
}
