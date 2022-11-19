package ROBOT.GUI;

import ROBOT.CarException;
import ROBOT.Logic.CompatibilitySupport;
import ROBOT.Logic.Interfaces.CarSensorInput;
import ROBOT.Logic.Interfaces.CarSensorInputEmulator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class CarSensorInputGUI extends Application implements CarSensorInput, CarSensorInputEmulator {

    private final HashMap<Sensor, Double> sensors = new HashMap<>();

    Label LabelFLValue, LabelFRValue, LabelBLValue, LabelBRValue;
    Slider SliderFL, SliderFR, SliderBL, SliderBR;

    Scene scene;
    Stage stage;
    Parent root;

    public CarSensorInputGUI() {
        for (Sensor s : Sensor.values())
            sensors.put(s, 255d);
    }

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
        System.out.println("[CarSensorInputGUI] Bye!");
        System.exit(0);
    }

    @Override
    public double getDistance(Sensor s) {
        return sensors.get(s);
    }

    @Override
    public void updateSensor(Sensor s, double value) throws CarException {
        sensors.put(s, value);
        sensors.put(CompatibilitySupport.invert(s), value);
    }

    private void readSensor(Sensor sensor) throws CarException {
        double val = getSlider(sensor).getValue();
        updateSensor(sensor, val);
    }

    public <T extends Node> T getNode(String id) {
        return getNode(root, id);
    }

    public void setCarSensors() {
        System.out.println("SET CAR SENSOR INPUT");

        double
                fl = getDistance(Sensor.FL),
                fr = getDistance(Sensor.FR),
                bl = getDistance(Sensor.BL),
                br = getDistance(Sensor.BR);

        LabelFLValue.setText("" + Math.round(fl));
        LabelFRValue.setText("" + Math.round(fr));
        LabelBLValue.setText("" + Math.round(bl));
        LabelBRValue.setText("" + Math.round(br));

        SliderFL.setValue(fl);
        SliderFR.setValue(fr);
        SliderBL.setValue(bl);
        SliderBR.setValue(br);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // toURL, but initial version did not work with loading resources so, we are using this until a better
        // alternative can be found
        URL newURL = new File("src/ROBOT/resources/sensor_input.fxml").toURI().toURL();
        System.out.println(newURL);
        assert newURL != null;

        root = FXMLLoader.load(newURL);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Input");
        stage.show();

        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                CarSensorInputGUI.quit();
            }
        });

        LabelFLValue = (Label) getNode("#LabelFLValue");
        LabelFRValue = (Label) getNode("#LabelFRValue");
        LabelBLValue = (Label) getNode("#LabelBLValue");
        LabelBRValue = (Label) getNode("#LabelBRValue");
        SliderFL = (Slider) getNode("#SliderFL");
        SliderFR = (Slider) getNode("#SliderFR");
        SliderBL = (Slider) getNode("#SliderBL");
        SliderBR = (Slider) getNode("#SliderBR");

        SliderFL.setOnMouseDragged(createEventHandler(Sensor.FL));
        SliderFL.setOnMousePressed(createEventHandler(Sensor.FL));
        SliderFR.setOnMouseDragged(createEventHandler(Sensor.FR));
        SliderFR.setOnMousePressed(createEventHandler(Sensor.FR));
        SliderBL.setOnMouseDragged(createEventHandler(Sensor.BL));
        SliderBL.setOnMousePressed(createEventHandler(Sensor.BL));
        SliderBR.setOnMouseDragged(createEventHandler(Sensor.BR));
        SliderBR.setOnMousePressed(createEventHandler(Sensor.BR));

        setCarSensors();
    }

    private EventHandler<MouseEvent> createEventHandler(final Sensor sensor) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    CarSensorInputGUI.this.getLabel(sensor).setText(
                            "" + (int) CarSensorInputGUI.this.getSlider(sensor).getValue()
                    );
                    readSensor(sensor);
                } catch (CarException ce) {
                    // Welp.
                }
            }
        };
    }

    private Slider getSlider(Sensor sensor) throws CarException {
        switch (CompatibilitySupport.onCorners(sensor)) {
            case FL:
                return SliderFL;
            case FR:
                return SliderFR;
            case BL:
                return SliderBL;
            case BR:
                return SliderBR;
            default:
                break;
        }
        throw new CarException("Slider: Impossible Sensor");
    }

    private Label getLabel(Sensor sensor) throws CarException {
        switch (CompatibilitySupport.onCorners(sensor)) {
            case FL:
                return LabelFLValue;
            case FR:
                return LabelFRValue;
            case BL:
                return LabelBLValue;
            case BR:
                return LabelBRValue;
            default:
                break;
        }
        throw new CarException("Label: Impossible Sensor");
    }

}
