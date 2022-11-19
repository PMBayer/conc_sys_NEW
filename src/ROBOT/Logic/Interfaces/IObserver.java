package ROBOT.Logic.Interfaces;

import ROBOT.Structure.SensorMap;

import java.util.ArrayList;

public interface IObserver {

    CarSensorInput getCarSensorInput();

    void setCarSensorInput(CarSensorInput input);

    ArrayList<Callback> getSubscribers();

    void subscribe(Callback callback);

    void unsubscribe(Callback callback);

    ArrayList<CarSensorInput.Sensor> getSensors();

    void observe(CarSensorInput.Sensor sensor);

    void ignore(CarSensorInput.Sensor sensor);

    void observeAll();

    void broadcast();

    void run();

    void interrupt();

    interface Callback {
        void call(CarSensorInput input, SensorMap map);
    }

}
