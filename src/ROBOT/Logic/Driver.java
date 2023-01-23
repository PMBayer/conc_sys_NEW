package ROBOT.Logic;

import ROBOT.CarException;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Interfaces.CarMotorOutput;

import java.util.Date;

/**
 * A Class that deals with the control of the vehicle
 * Here are Commands defined that will later be executed by the vehicle.
 * In this Class there are no rules defined; it just serves to define Commands
 */
public class Driver {

    CarMotorOutput output;
    Commands currentCommand;
    private int speedForward = 250;
    private int speedBackward = -150;
    private int steeringValue = 160;
    private long lockedTill = 0;

    /**
     * Drive Constructor.
     * <p>
     * defines used motors and stops them.
     * If no CarMotorOutput instance is available, an anonymous logger is used
     *
     * @param output the motors in use.
     * @throws CarException idk why tbh
     */
    public Driver(CarMotorOutput output) throws CarException {
        if (output == null) {
            this.output = new CarMotorOutput() {
                @Override
                public void setSpeed(int x) throws CarException {
                    MyLogger.info("[DRIVE] setSpeed: " + x);
                }

                @Override
                public void steering(int x) throws CarException {
                    MyLogger.info("[Drive] steering: " + x);
                }
            };
        } else this.output = output;
        halt();
    }

    public Commands getCommand() {
        return currentCommand;
    }

    /**
     * Method that adjusts steering and speed according to the given command
     **/
    void setCommand(Commands command) throws CarException {
        this.currentCommand = command;

        switch (command) {
            case FORWARD:
                output.steering(0);
                output.setSpeed(-speedForward);
                break;

            case REVERSE:
                output.steering(0);
                output.setSpeed(-speedBackward);
                break;

            case LEFT:
                output.steering(steeringValue);
                output.setSpeed(-speedForward);
                break;

            case RIGHT:
                output.steering(-steeringValue);
                output.setSpeed(-speedForward);
                break;

            case REV_LEFT:
                output.steering(steeringValue);
                output.setSpeed(-speedBackward);
                break;

            case REV_RIGHT:
                output.steering(-steeringValue);
                output.setSpeed(-speedBackward);
                break;

            case HALT:
                output.steering(0);
                output.setSpeed(0);
                break;
        }
        MyLogger.debug("Manoeuvring: " + currentCommand.name());
    }

    public int getSpeedForward() {
        return speedForward;
    }

    public void setSpeedForward(int speedForward) {
        this.speedForward = adjustInt(speedForward);
    }

    public int getSteeringValue() {
        return steeringValue;
    }

    public void setSteeringValue(int steeringValue) {
        this.steeringValue = -adjustInt(steeringValue);
    }

    public int getSpeedBackward() {
        return speedBackward;
    }

    public void setSpeedBackward(int speedBackward) {
        this.speedBackward = adjustInt(speedBackward);
    }

    /**
     * adjusts an Integer Value e.g 0 - 100 and lose it's prefix
     **/
    private int adjustInt(int value) {
        return Math.min(Math.abs(value), 100);
    }

    /**
     * Methods to manually set a certain command
     **/

    void halt() throws CarException {
        setCommand(Commands.HALT);
    }

    void forward() throws CarException {
        setCommand(Commands.FORWARD);
    }

    void left() throws CarException {
        setCommand(Commands.LEFT);
    }

    void right() throws CarException {
        setCommand(Commands.RIGHT);
    }

    void reverse() throws CarException {
        setCommand(Commands.REVERSE);
    }

    void revLeft() throws CarException {
        setCommand(Commands.REV_LEFT);
    }

    void revRight() throws CarException {
        setCommand(Commands.REV_RIGHT);
    }

    void lock(int ms) {
        lockedTill = new Date().getTime() + ms;
    }

    void unlock() {
        lockedTill = 0;
    }

    boolean isLocked() {
        return lockedTill > new Date().getTime();
    }

    public enum Commands {
        FORWARD,
        REVERSE,
        LEFT,
        RIGHT,
        REV_LEFT,
        REV_RIGHT,
        HALT
    }
}
