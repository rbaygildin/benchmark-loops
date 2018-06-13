package org.rbaygildin;

import org.rbaygildin.config.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.File;

/**
 * Controls order of application execution, shows UI and provides
 * cleaning of resources
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class MainApp extends Application {

    {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public static void main(String[] args){
        launch(args);
    }

    /**
     * Start application execution
     *
     * @param primaryStage Primary stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory((clazz) -> context.getBean(clazz));
        if(System.getProperty("os.name") != null && System.getProperty("os.name").startsWith("Windows"))
            loader.setLocation(getClass().getResource("\\main.fxml"));
        else
            loader.setLocation(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Angie Benchmark");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Initialize application
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    /**
     * Halt down application and clear resources
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    private ConfigurableApplicationContext context;
}
