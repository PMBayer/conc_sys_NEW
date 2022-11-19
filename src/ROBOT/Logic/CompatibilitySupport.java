package ROBOT.Logic;

import ROBOT.Logic.Interfaces.CarSensorInput.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * A Class that is meant to support the two different Sensor Placements
 * Sensors on the Sides or on the corners
 */
public class CompatibilitySupport {

    private static List<Sensor> sensorsOnSides = Arrays.asList(Sensor.F, Sensor.B, Sensor.R, Sensor.L);
    private static List<Sensor> sensorsOnCorners = Arrays.asList(Sensor.FL, Sensor.BR, Sensor.FR, Sensor.BL);

    public static Sensor invert(Sensor s) {
        switch (s) {
            default:
            case F:
                return Sensor.FL;
            case B:
                return Sensor.BR;
            case R:
                return Sensor.FR;
            case L:
                return Sensor.BL;
            case FL:
                return Sensor.F;
            case BR:
                return Sensor.B;
            case FR:
                return Sensor.R;
            case BL:
                return Sensor.L;
        }
    }

    public static Sensor onSides(Sensor s) {
        if (sensorsOnSides.contains(s))
            return s;
        return invert(s);
    }

    public static Sensor onCorners(Sensor s) {
        if (sensorsOnCorners.contains(s))
            return s;
        return invert(s);
    }
}
