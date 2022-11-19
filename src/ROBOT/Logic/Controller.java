package ROBOT.Logic;

import ROBOT.CarException;
import ROBOT.Logic.Driver.Commands;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Structure.SensorMap;

public class Controller {

    /**
     * Order of commands the vehicle is supposed to try before coming to a halt.
     */
    static final Commands[] PREFERED_DRIVING_ORDER = new Commands[]{
            Commands.FORWARD,
            Commands.LEFT,
            Commands.RIGHT,
            //Commands.REVERSE,
            Commands.REV_LEFT,
            Commands.REV_RIGHT,
            Commands.HALT
    };
    private final Observer observer;
    private final Navigator navigator;
    private Driver driver;

    public Controller(CarSensorInput sInput) throws Exception {
        this(sInput, null);
    }

    public Controller(CarSensorInput sInput, CarMotorOutput mOutput) throws Exception {
        navigator = new Navigator();

        setMotorOutput(mOutput);

        observer = new Observer();
        observer.setCarSensorInput(sInput);
        observer.observeAll();
        observer.subscribe(new Observer.Callback() {
            @Override
            public void call(CarSensorInput input, SensorMap map) {
                Controller.this.handleOutput(map);
            }
        });
    }

    public Navigator getNavi() {
        return this.navigator;
    }

    public Observer getObserver() {
        return this.observer;
    }

    public Driver getDrive() {
        return this.driver;
    }

    private void handleOutput(SensorMap map) {
        navigator.update(map);
        Commands coms = navigator.navigate();

        try {
            driver.setCommand(coms);
        } catch (CarException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        observer.start();
    }

    public void setMotorOutput(CarMotorOutput mOutput) throws CarException {
        this.driver = new Driver(mOutput);
    }
}
