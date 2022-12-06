package ROBOT.Applications;

import ROBOT.GUI.CarMotorOutputGUI;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;
import ROBOT.Tests.SiLTest;

import java.util.List;

public class SiLGUI extends AutoGUI {

    public static void main(String[] args) {
        MyLogger.setLogLevel(MyLogger.Level.DEBUG);
        launch(args);
    }

    @Override
    protected void init(List<String> args) throws Exception {
        final SiLTest input = new SiLTest();
        CarMotorOutputGUI outputGUI = createGUI(new CarMotorOutputGUI());

        Controller controller = new Controller(input, outputGUI);
        outputGUI.controlledBy(controller);

        controller.start();
        input.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    input.join();
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();
    }
}
