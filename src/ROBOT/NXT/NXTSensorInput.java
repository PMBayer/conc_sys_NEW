package ROBOT.NXT;

import ROBOT.CarException;
import ROBOT.Logic.Interfaces.CarSensorInput;

public class NXTSensorInput implements CarSensorInput {
    @Override
    public double getDistance(Sensor s) throws CarException {
        return 0;
    }
}
