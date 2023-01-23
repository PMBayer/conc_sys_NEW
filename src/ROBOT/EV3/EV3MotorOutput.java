package ROBOT.EV3;

import ROBOT.CarException;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class EV3MotorOutput implements CarMotorOutput {

    public static final int MAX_ANGLE = 50;

    private EV3LargeRegulatedMotor driveMotorR = new EV3LargeRegulatedMotor(MotorPort.A);
    private EV3LargeRegulatedMotor driveMotorL = new EV3LargeRegulatedMotor(MotorPort.B);
    private EV3LargeRegulatedMotor steeringMotor = new EV3LargeRegulatedMotor(MotorPort.C);

    public EV3MotorOutput() {
        driveMotorL.synchronizeWith(new EV3LargeRegulatedMotor[]{driveMotorR});
        steeringMotor.resetTachoCount();
        steeringMotor.rotateTo(0);
    }

    public static int degreeify(int x) {
        return Math.round(MAX_ANGLE * x / 100);
    }

    @Override
    public void setSpeed(int x) throws CarException {
        syncMotors(true);
        driveMotorR.setSpeed(Math.abs(x));
        driveMotorL.setSpeed(Math.abs(x));
        if (x > 0) {
            driveMotorR.forward();
            driveMotorL.forward();
        } else if (x < 0) {
            driveMotorR.backward();
            driveMotorL.backward();
        } else {
            driveMotorR.stop();
            driveMotorL.stop();
        }
        syncMotors(false);
    }

    @Override
    public void steering(int x) throws CarException {
        steeringMotor.rotateTo(degreeify(x), true);
    }

    private void syncMotors(boolean enable) {
        if (enable)
            driveMotorL.startSynchronization();
        else
            driveMotorL.endSynchronization();
    }
}
