package ROBOT.Applications;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public abstract class AutoGUI extends Application {

    public static <T extends Application> T createGUI(T app) throws Exception {
        app.start(new Stage());
        return app;
    }

    protected abstract void init(List<String> args) throws Exception;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parameters params = getParameters();
        final List<String> args = params.getRaw();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    AutoGUI.this.init(args);
                } catch (Exception e) {
                    Platform.exit();
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

}
