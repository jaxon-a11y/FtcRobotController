package org.firstinspires.ftc.teamcode;

public class Toggle {

    private boolean last = false;
    private boolean state = false;

    public boolean update(boolean pressed) {
        if (pressed && !last) {
            state = !state;
        }
        last = pressed;
        return state;
    }

    public boolean get() {
        return state;
    }
}
