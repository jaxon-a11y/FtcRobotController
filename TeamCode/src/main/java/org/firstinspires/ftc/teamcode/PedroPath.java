package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;

public class PedroPath {
    private final List<PathPoint> points = new ArrayList<>();

    public PedroPath add(double x, double y, double heading) {
        points.add(new PathPoint(x, y, heading));
        return this;
    }

    public List<PathPoint> getPoints() {
        return points;
    }

    public int size() {
        return points.size();
    }

    public PathPoint get(int i) {
        return points.get(i);
    }
}
