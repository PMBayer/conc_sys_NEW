package ROBOT.GUI;

import ROBOT.CarException;
import ROBOT.Logger.MyLogger;
import ROBOT.Logic.Controller;
import ROBOT.Logic.Driver;
import ROBOT.Logic.Interfaces.CarMotorOutput;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Logic.Interfaces.CarSensorInput.Sensor;
import ROBOT.Logic.Interfaces.IObserver;
import ROBOT.Structure.SensorMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class CarMotorOutputGUI extends Application implements CarMotorOutput {

    Scene scene;
    Stage stage;
    Parent root;

    TextField valFL, valFR, valBL, valBR, valSpeed, valSteering, valManoeuvre;

    @SuppressWarnings("unchecked")
    public static <T extends Node> T getNode(Node root, String id) {
        final Node node = root.lookup(id);
        if (node == null)
            throw new NullPointerException("cannot find child node fx:id for argument: " + id);

        return (T) node;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void quit() {
        Platform.exit();
        System.out.println("[CarMotorOutputGUI] Bye!");
        System.exit(0);
    }

    public <T extends Node> T getNode(String id) {
        return getNode(root, id);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // toURL, but initial version did not work with loading resources so, we are using this until a better
        // alternative can be found
        URL newURL = new File("src/ROBOT/resources/output_table.fxml").toURI().toURL();
        MyLogger.debug(newURL);
        assert newURL != null;

        root = FXMLLoader.load(newURL);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("WummOutput");
        stage.show();

        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                CarMotorOutputGUI.quit();
            }
        });

        valFL = getNode("#valFL");
        valFR = getNode("#valFR");
        valBL = getNode("#valBL");
        valBR = getNode("#valBR");
        valSpeed = getNode("#valSpeed");
        valSteering = getNode("#valSteering");
        valManoeuvre = getNode("#valManeuvre");
    }

    @Override
    public void setSpeed(int x) throws CarException {
        MyLogger.debug("setSpeed: " + x);
        setTextFieldText(valSpeed, x);
    }

    @Override
    public void steering(int x) throws CarException {
        MyLogger.debug("setSteering: " + x);
        setTextFieldText(valSteering, x);
    }

    public void setSensorValue(Sensor sensor, int value) {
        switch (sensor) {
            case FL:
                setTextFieldText(valFL, value);
                break;
            case FR:
                setTextFieldText(valFR, value);
                break;
            case BL:
                setTextFieldText(valBL, value);
                break;
            case BR:
                setTextFieldText(valBR, value);
                break;
            default:
                break;
        }
    }

    private void setTextFieldText(final TextField textField, final int value) {
        setTextFieldText(textField, "" + value);
    }

    private void setTextFieldText(final TextField textField, final String value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    textField.setText(value);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        });
    }

    public void setManoeuvre(Driver.Commands command) {
        setTextFieldText(valManoeuvre, command.name());
    }

    public void controlledBy(final Controller controller) {
        controller.getObserver().subscribe(new IObserver.Callback() {
            @Override
            public void call(CarSensorInput input, SensorMap map) {
                for (Map.Entry<Sensor, Double> entry : map.entrySet()) {
                    CarMotorOutputGUI.this.setSensorValue(entry.getKey(), entry.getValue().intValue());
                }
                CarMotorOutputGUI.this.setManoeuvre(controller.getDrive().getCommand());
            }
        });
    }

}
