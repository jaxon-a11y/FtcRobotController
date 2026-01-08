package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Servo2 {

    private CRServo leftServo;
    private CRServo rightServo;

    private final ElapsedTime timer = new ElapsedTime();

    // Constants
    private static final double SPIN_DURATION = 0.35;   // seconds
    private static final double SPIN_POWER = 1.0;

    private boolean spinning = false;
    private boolean lastButton = false;

    public Servo2() {}

    public void init(HardwareMap hw) {
        leftServo  = hw.get(CRServo.class, "left_middle");
        rightServo = hw.get(CRServo.class, "right_middle");

        // Typically correct directions:
        leftServo.setDirection(CRServo.Direction.FORWARD);
        rightServo.setDirection(CRServo.Direction.REVERSE);
    }

    /**
     * Runs once per loop; starts timed spin on button press.
     * @param pressed button input (e.g. gamepad1.b)
     */
    public void loop(boolean pressed) {

        // Start spin when button is pressed (edge-trigger)
        if (pressed && !lastButton && !spinning) {
            spinning = true;
            timer.reset();

            leftServo.setPower(SPIN_POWER);
            rightServo.setPower(SPIN_POWER);
        }

        // Stop after duration
        if (spinning && timer.seconds() >= SPIN_DURATION) {
            stop();
            spinning = false;
        }

        lastButton = pressed;
    }

    /** Immediately stop both servos */
    public void stop() {
        leftServo.setPower(0);
        rightServo.setPower(0);
    }

    /** Returns whether servos are currently spinning */
    public boolean isSpinning() {
        return spinning;
    }
    public void manualStart() {
        spinning = true;
        leftServo.setPower(SPIN_POWER);
        rightServo.setPower(SPIN_POWER);
        timer.reset();
    }
}
