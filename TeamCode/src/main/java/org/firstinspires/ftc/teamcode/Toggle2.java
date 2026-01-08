package org.firstinspires.ftc.teamcode;

public class Toggle2 {

    private boolean state = false;
    private boolean lastInput = false;

    /**
     * Call this every loop with the button input.
     * Returns the current toggle state.
     */
    public boolean update(boolean buttonPressed) {

        if (buttonPressed && !lastInput) {
            state = !state;
        }

        lastInput = buttonPressed;
        return state;
    }

    /** Returns the current toggle value without updating it */
    public boolean getState() {
        return state;
    }
}
