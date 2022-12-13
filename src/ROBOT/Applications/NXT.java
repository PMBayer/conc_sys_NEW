package ROBOT.Applications;

import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Logic.Observer;
import ROBOT.NXT.NXTMotorOutput;
import ROBOT.NXT.NXTSensorInput;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

import java.util.HashMap;

public class NXT {
    public static void main(String[] args) {
        MyLogger.setLogLevel(MyLogger.Level.INFO);

        Observer.SLEEP_INTERVAL = 400;

        HashMap<String, Integer> test = new HashMap<>();
        test.put("asdf", 1234);

        test.keySet();

        try {

            LCD.drawString("Oke.", 0, 6);

            CarSensorInput input = new NXTSensorInput();
            CarMotorOutput output = new NXTMotorOutput();

            Controller controller = new Controller(input, output);

            LCD.drawString("Initialisiert.", 0, 7);
            Button.waitForAnyPress();

            controller.start();

            LCD.drawString("Gestartet.", 0, 2);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
