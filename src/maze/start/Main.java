package maze.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/maze/fxml/main.fxml"));
        Scene scene = new Scene(root, 1150, 720);
        scene.getStylesheets().add("/maze/css/style.css");
        
        stage.setTitle("Моделирование лабиринта и поиск выхода из него");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
