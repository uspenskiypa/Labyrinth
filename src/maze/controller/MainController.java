package maze.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import maze.objects.Labyrinth;


public class MainController {
    @FXML
    private RadioButton rbAlgPrim; //переключатель "Прима"
    
    @FXML
    private RadioButton rbAlgRight; //переключатель "Правой руки"
    
    @FXML
    private RadioButton rbModeAuto; //переключатель "Автоматический"
    
    @FXML
    private  GridPane pnGridMaze; //переключатель "Автоматический"
    
    //Обработчик нажатия на кнопку "Сгенерировать"
    public void btGenerateButtonAction(ActionEvent actionEvent) {
        int rows = 11; //количество строк
        int columns = 11; //количество столбцов
        Labyrinth maze = new Labyrinth(columns,rows);
        pnGridMaze.getChildren().clear();
        if (rbAlgPrim.isSelected()) {
            maze.runPrimAlg(columns, rows); //использование алгоритма Прима
        }           
        else {
            
        }
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("maze-location");
                if (maze.getLocation(i, j).rightWall == true) {
                    pane.getStyleClass().add("right");
                }
                if (maze.getLocation(i, j).downWall == true) {
                    pane.getStyleClass().add("down");
                }
                pnGridMaze.add(pane, i, j);
            }
        } 
    }
    
    //Обработчик нажатия на кнопку "Начать проходить"
    public void btStartButtonAction(ActionEvent actionEvent) {
        
    }
    
    //Обработчик нажатия на кнопку "Пройти"
    public void btAutoGoButtonAction(ActionEvent actionEvent) {
        
    }
    
    //Обработчик нажатия на кнопку "Сделать шаг"
    public void btStepButtonAction(ActionEvent actionEvent) {
        
    }
    
    //Обработчик нажатия на кнопку "Выход"
    public void btExitButtonAction(ActionEvent actionEvent) {
         Platform.exit();
    }
}
