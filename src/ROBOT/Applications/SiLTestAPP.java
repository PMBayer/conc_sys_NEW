package ROBOT.Applications;

import ROBOT.Logic.Controller;
import ROBOT.Tests.SiLTest;

public class SiLTestAPP {
    public static void main(String[] args) throws Exception {
        SiLTest test = new SiLTest();
        Controller controller = new Controller(test, test);
        controller.start();

        test.validateDrive(controller.getDrive());
        test.start();

        test.join();
        System.exit(0);
    }
}
