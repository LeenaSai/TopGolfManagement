package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Adjusted path for FXML file loading
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        
        if (loader.getLocation() == null) {
            System.out.println("FXML file not found!");
        }
        
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Top Golf Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
