package ROBOT.EV3;

import ROBOT.CarException;
import ROBOT.Logic.CompatibilitySupport;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Structure.SensorMap;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.SampleProvider;


public class EV3SensorInput implements CarSensorInput {

    private final NXTUltrasonicSensor s1;
    private final NXTUltrasonicSensor s2;
    private final NXTUltrasonicSensor s3;
    private final NXTUltrasonicSensor s4;

    private final SensorMap values = new SensorMap();

    public EV3SensorInput() throws CarException {
        try {

            s1 = new NXTUltrasonicSensor(SensorPort.S1);
            s2 = new NXTUltrasonicSensor(SensorPort.S2);
            s3 = new NXTUltrasonicSensor(SensorPort.S3);
            s4 = new NXTUltrasonicSensor(SensorPort.S4);

        } catch (Exception e) {

            throw new CarException("Fehler mit Sensoren");

        }

        for (Sensor sensor : Sensor.values()) {
            try {
                getSonic(sensor).enable();
                values.put(sensor, 255d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void printSensorValue(Sensor s, Double val) {
        printSensorValue(s, val.toString());
    }

    public static void printSensorValue(Sensor s, String val) {
        switch (CompatibilitySupport.onSides(s)) {
            case F:
                LCD.drawString("F: " + val, 0, 0);
                break;
            case B:
                LCD.drawString("B: " + val, 0, 3);
                break;
            case R:
                LCD.drawString("R: " + val, 0, 1);
                break;
            case L:
                LCD.drawString("L: " + val, 0, 2);
                break;
            default:
                break;
        }
    }

    public NXTUltrasonicSensor getSonic(Sensor sensor) throws CarException {
        switch (sensor) {
            case F:
            case FL:
                return s1;
            case B:
            case BR:
                return s2;
            case R:
            case FR:
                return s3;
            case L:
            case BL:
                return s4;
            default:
                throw new CarException("Invalid Sensor");
        }
    }

    @Override
    public double getDistance(Sensor s) throws CarException {
        try {

            double val;

            try {

                SampleProvider sp = getSonic(s).getDistanceMode();
                float[] sample = new float[sp.sampleSize()];
                sp.fetchSample(sample, 0);

                val = (double) sample[0];

            } catch (Exception e) {

                val = 2.55d;

            }

            if (Double.isInfinite(val)) val = 2.55d;

            val *= 100;
            values.put(s, val);
            values.put(CompatibilitySupport.invert(s), val);

            printSensorValue(s, val);
            return val;

        } catch (Exception e) {

            printSensorValue(s, "0/error");
            return 0d;

        }
    }
}
