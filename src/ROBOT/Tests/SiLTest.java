package ROBOT.Tests;

import ROBOT.CarException;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Driver;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Logic.Interfaces.CarSensorInput;

import java.util.HashMap;

public class SiLTest extends Thread implements CarSensorInput, CarMotorOutput {

    int currentSpeed = 0;
    int currentSteering = 0;
    HashMap<Sensor, Double> distances;
    private boolean checkOutput = false;
    private Driver driver;

    public SiLTest() throws Exception {
        driver = new Driver(this);
        distances = new HashMap<>();
        for (Sensor s : Sensor.values()) {
            distances.put(s, 255d);
        }
    }

    @Override
    public void run() {
        try {
            this.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean test() throws Exception {
        MyLogger.info("Launching SiL Test");

        Thread.sleep(1000);
        testSequence1();
        Thread.sleep(500);
        testSequence2();
        Thread.sleep(100);

        return true;
    }

    public void testSequence1() throws InterruptedException, CarException {
        MyLogger.info("[Test Sequence 1] running");

        // Robot should drive forward
        distances.put(Sensor.FL, 120d);
        distances.put(Sensor.FR, 224d);
        distances.put(Sensor.BL, 202d);
        distances.put(Sensor.BR, 82d);

        Thread.sleep(3000);

        // Check if Robot drives forward
        verify(currentSpeed, driver.getSpeedForward());
        verify(currentSteering, 0);

        Thread.sleep(500);

        // Robot should drive to the right now; obstacle front left of robot
        distances.put(Sensor.FL, 30d);

        Thread.sleep(3000);

        //Check, if  robot drives to right
        verify(currentSpeed, driver.getSpeedForward());
        verify(currentSteering, driver.getSteeringValue());

        Thread.sleep(500);

        // Robot should be driving in reverse (no space in front of robot)
        distances.put(Sensor.FL, 32d);
        distances.put(Sensor.FR, 28d);

        Thread.sleep(3000);

        //Check, if robots drives reverse
        verify(currentSpeed, driver.getSpeedBackward());
        verify(currentSteering, driver.getSteeringValue());

        Thread.sleep(500);

        if (checkOutput) MyLogger.info("[Test Sequence 1] success");
    }

    public void testSequence2() throws InterruptedException, CarException {
        MyLogger.info("[Test Sequence 2] running");

        // robot is placed right in front of an obstacle
        // to the right behind him there is an obstacle as well
        //robot should drive in reverse to the left
        distances.put(Sensor.FL, 20d);
        distances.put(Sensor.FR, 23d);
        distances.put(Sensor.BL, 202d);
        distances.put(Sensor.BR, 40d);

        Thread.sleep(3000);

        //Chech if robot drives reverse to the left
        verify(currentSpeed, -driver.getSpeedBackward());
        verify(currentSteering, driver.getSteeringValue());

        //an obstacle appeared behind the robot -> should drive forward since there is space now
        distances.put(Sensor.BL, 20d);
        distances.put(Sensor.FL, 220d);
        distances.put(Sensor.FR, 150d);

        Thread.sleep(3000);

        //check if robot drives forward
        verify(currentSpeed, -driver.getSpeedForward());
        verify(currentSteering, 0);

        Thread.sleep(500);

        //another vehicle appears in front right to the robot -> should drive left
        distances.put(Sensor.FR, 30d);

        Thread.sleep(3000);

        //check if robot is driving to the left
        verify(currentSpeed, -driver.getSpeedForward());
        verify(currentSteering, driver.getSteeringValue());

        Thread.sleep(500);

        //robot is blocked from front and behind -> should stop/halt
        distances.put(Sensor.FL, 10d);
        distances.put(Sensor.BR, 32d);

        Thread.sleep(3000);

        // Check if robot does stop
        verify(currentSpeed, 0);
        verify(currentSteering, 0);

        Thread.sleep(500);

        if (checkOutput) MyLogger.info("[Test Sequence 2] success");
    }

    /**
     * Method that verifies if actual value corresponds to the expected Result
     */
    public <T extends Comparable<T>> void verify(T actualResult, T expectedResult) throws CarException {
        if (checkOutput) {
            if (!actualResult.equals(expectedResult)) {
                throw new CarException("NEIN! Soll: " + expectedResult.toString() + " | Ist: " + actualResult.toString());
            }
            MyLogger.info("Test successfull");
        }
    }

    public void validateDrive(Driver driver) throws Exception {
        this.checkOutput = true;
        this.driver = driver;
    }

    /**
     * Override Methods
     */

    @Override
    public void setSpeed(int x) throws CarException {
        currentSpeed = x;
        MyLogger.debug("[SiL] setSpeed: " + x);
    }

    @Override
    public void steering(int x) throws CarException {
        MyLogger.debug("[SiL] steering: " + x);
        currentSteering = x;
    }

    @Override
    public double getDistance(Sensor s) throws CarException {
        return distances.get(s);
    }
}
