package ROBOT.Applications;

import ROBOT.EV3.EV3MotorOutput;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Tests.SiLTest;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class SiLEV3 {
    public static void main(String[] args) {
        MyLogger.setLogLevel(MyLogger.Level.INFO);

        LCD.drawString("Oki :)", 4, 4);
        Delay.msDelay(500);

        try {
            SiLTest input = new SiLTest();
            CarMotorOutput output = new EV3MotorOutput();

            Controller controller = new Controller(input, output);
            controller.start();

            Delay.msDelay(1000);

            input.start();
            input.join();

        } catch (Exception e) {
            e.printStackTrace();
            Delay.msDelay(5000);
            System.exit(1);
        }
        System.exit(0);
    }


}
