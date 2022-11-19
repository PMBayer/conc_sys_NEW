package ROBOT.Logic.Interfaces;

import ROBOT.CarException;

public interface CarSensorInputEmulator {

    void updateSensor(CarSensorInput.Sensor s, double value) throws CarException;

}
