package ROBOT.Applications;

import ROBOT.EV3.EV3MotorOutput;
import ROBOT.EV3.EV3SensorInput;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Logic.Observer;

public class EV3 {
    public static void main(String[] args) {
        MyLogger.setLogLevel(MyLogger.Level.INFO);

        Observer.SLEEP_INTERVAL = 50;
        Observer.CORRECTION_COUNT = 10;

        try {
            CarSensorInput input = new EV3SensorInput();
            CarMotorOutput output = new EV3MotorOutput();
            Controller controller = new Controller(input, output);
            controller.start();

        } catch (Exception e) {
            e.printStackTrace();
            //Delay.msDelay(5000);
            System.exit(1);
        }

        System.exit(0);

    }
}
