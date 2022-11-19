package ROBOT.Logic;

import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Logic.Interfaces.IObserver;
import ROBOT.Structure.SensorList;
import ROBOT.Structure.SensorMap;

import java.util.ArrayList;

public class Observer extends Thread implements IObserver {

    public static int SLEEP_INTERVAL = 100;
    public static int CORRECTION_COUNT = 1;
    ArrayList<Callback> subscribers = new ArrayList<>();
    SensorList sensors = new SensorList();
    private CarSensorInput carSensorInput;
    private Integer sleepInterval = null;

    public CarSensorInput getCarSensorInput() {
        return carSensorInput;
    }

    public void setCarSensorInput(CarSensorInput input) {
        carSensorInput = input;
    }

    public ArrayList<Callback> getSubscribers() {
        return subscribers;
    }

    /**
     * Subscribe to Event -> generate event handler
     *
     * @param callback The subscriber that is meant to be added
     **/
    @Override
    public void subscribe(Callback callback) {
        subscribers.add(callback);
    }

    /**
     * unsubscribe event
     *
     * @param callback remove subscriber
     */
    @Override
    public void unsubscribe(Callback callback) {
        subscribers.remove(callback);
    }

    @Override
    public SensorList getSensors() {
        return sensors;
    }

    @Override
    public void observe(CarSensorInput.Sensor sensor) {
        if (!sensors.contains(sensor))
            sensors.add(sensor);
    }

    @Override
    public void ignore(CarSensorInput.Sensor sensor) {
        sensors.remove(sensor);
    }

    @Override
    public void observeAll() {
        for (final CarSensorInput.Sensor s : CarSensorInput.Sensor.values())
            observe(s);
    }

    //broadcast method; every observer update
    @Override
    public void broadcast() {
        SensorMap map = SensorMap.readValues(carSensorInput);
        for (Callback sub : subscribers) {
            sub.call(carSensorInput, map);
        }
    }

    public int getSleepInterval() {
        if (sleepInterval == null)
            return SLEEP_INTERVAL;
        return sleepInterval;
    }

    public void setSleepInterval(int ms) {
        this.sleepInterval = ms;
    }

    @Override
    public void run() {
        final int sleep = getSleepInterval();
        try {
            while (true) {
                broadcast();
                Thread.sleep(sleep);
            }
        } catch (InterruptedException e) {
            // ok
        }
    }
}
