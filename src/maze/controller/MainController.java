package maze.controller;

import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    
    Random rn = new Random();
    Labyrinth maze;

    //Обработчик нажатия на кнопку "Сгенерировать"
    public void btGenerateButtonAction(ActionEvent actionEvent) {
        int rows = 11; //количество строк
        int columns = 11; //количество столбцов
        maze = new Labyrinth(columns,rows);
        pnGridMaze.getChildren().clear();
        if (rbAlgPrim.isSelected()) {
            maze.runPrimAlg(columns, rows); //использование алгоритма Прима
        }           
        else {
            maze.runKruskalAlg(columns, rows); //использование алгоритма Прима
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
        Pane firstPane = randomStart(columns, rows, true);
        Pane secondPane = randomStart(columns, rows, false);
        while (firstPane.equals(secondPane)) {
            firstPane = randomStart(columns, rows, true);
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
    
    //Возвращает случайно выбранную граничную локацию лабиринта.
    //если isExit - true, то локация - выход из лабиринта, иначе вход.
    //Изменяет внешний вид (стиль) локации.
    public Pane randomStart(int columns, int rows, boolean isExit) {
        int i = 0, j = 0;
        switch(rn.nextInt(4)) {
            case 0: {
                i = 0;
                j = rn.nextInt(rows);
                break;
            }
            case 1: {
                i = columns-1;
                j = rn.nextInt(rows);
                break;
            }
            case 2: {
                i = rn.nextInt(columns);
                j = 0;
                break;
            }
            case 3: {
                i = rn.nextInt(columns);
                j = rows-1;
                break;
            }
        }
        Pane pane = (Pane) getRandomNode(pnGridMaze, i, j);
        pane.getStyleClass().clear();
        if (isExit) {
            pane.getStyleClass().add("maze-exit");
            maze.setExitX(i);
            maze.setExitY(j);
        } 
        else {
            pane.getStyleClass().add("maze-entry");
            maze.setEntryX(i);
            maze.setEntryY(j);
        }
        if (maze.getLocation(i, j).rightWall == true) {
            pane.getStyleClass().add("right");
        }
        if (maze.getLocation(i, j).downWall == true) {
            pane.getStyleClass().add("down");
        }
        return pane;
    }
    
    //Возвращает объект панели по индексу
    private Node getRandomNode(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    } 
}
