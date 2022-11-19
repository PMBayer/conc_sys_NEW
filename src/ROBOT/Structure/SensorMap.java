package ROBOT.Structure;

import ROBOT.Logic.Interfaces.CarSensorInput;

import java.util.HashMap;

public class SensorMap extends HashMap<CarSensorInput.Sensor, Double> {

    private static final long serialVersionUID = 4742440922505125966L;

    //initialize SensorMap with a value of 255 for every sensor
    public static SensorMap init() {
        return filled(255d);
    }

    //initial fill of sensor map with chosen value
    public static SensorMap filled(Double value) {
        SensorMap map = new SensorMap();
        for (CarSensorInput.Sensor s : CarSensorInput.Sensor.values()) {
            map.put(s, value);
        }
        return map;
    }

    public static SensorMap readValues(CarSensorInput input) {
        SensorMap map = new SensorMap();
        for (CarSensorInput.Sensor sensor : CarSensorInput.Sensor.values()) {
            try {
                map.put(sensor, input.getDistance(sensor));
            } catch (Exception e) {
                map.put(sensor, 0d);
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Double sanitizeSensorValue(Double value) {
        if (value.isInfinite()) return null;
        if (value > 255d) return 255d;
        if (value < 0) return 0d;
        return value;
    }

}
