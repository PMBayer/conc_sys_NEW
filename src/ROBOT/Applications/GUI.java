package ROBOT.Applications;

import ROBOT.CarException;
import ROBOT.GUI.CarMotorOutputGUI;
import ROBOT.GUI.CarSensorInputGUI;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;

import java.util.List;

public class GUI extends AutoGUI {

    public static void main(String[] args) throws InterruptedException, CarException {
        MyLogger.setLogLevel(MyLogger.Level.DEBUG);
        launch(args);
    }

    @Override
    public void init(List<String> args) throws Exception {

        CarSensorInputGUI inputGUI = createGUI(new CarSensorInputGUI());
        CarMotorOutputGUI outputGUI = createGUI(new CarMotorOutputGUI());

        Controller controller = new Controller(inputGUI, outputGUI);
        outputGUI.controlledBy(controller);

        controller.start();

    }

}
