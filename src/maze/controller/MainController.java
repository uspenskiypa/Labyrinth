package maze.controller;

import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import maze.objects.Labyrinth;
import maze.objects.Mouse;


public class MainController {
    @FXML
    private RadioButton rbAlgPrim; //переключатель "Прима"
    
    @FXML
    private RadioButton rbAlgRight; //переключатель "Правой руки"
     
    @FXML
    private RadioButton rbModeAuto; //переключатель "Автоматический"
    
    @FXML
    private  GridPane pnGridMaze; //Панель с сеткой
    
    @FXML
    private  Button btAutoGo; //кнопка "Пройти"
    
    @FXML
    private  Button btStep; //кнопка "Сделать шаг"
    
    @FXML
    private  Button btStart; //кнопка "Начать проходить"
    
    Random rn = new Random();
    Labyrinth maze;
    Mouse mouse;
    Image image = new Image(getClass().getResourceAsStream("/maze/icons/mouse.png"));
    ImageView imageView = new ImageView(image);
    

    //Обработчик нажатия на кнопку "Сгенерировать"
    public void btGenerateButtonAction(ActionEvent actionEvent) {
        int rows = 11; //количество строк
        int columns = 11; //количество столбцов
        maze = new Labyrinth(columns,rows);
        pnGridMaze.getChildren().clear();
        if (rbAlgPrim.isSelected()) {
            maze.runPrimAlg(); //использование алгоритма Прима
        }           
        else {
            maze.runKruskalAlg(); //использование алгоритма Прима
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
        btAutoGo.setDisable(true);
        btStep.setDisable(true);
        btStart.setDisable(false);
    }
    
    //Обработчик нажатия на кнопку "Начать проходить"
    public void btStartButtonAction(ActionEvent actionEvent) {
        btStart.setDisable(true);     
        if (rbModeAuto.isSelected()) {
            btAutoGo.setDisable(false);
        }
        else {
            btStep.setDisable(false);
        }
        int entryX = maze.getEntryX();
        int entryY = maze.getEntryY();
        mouse = new Mouse(entryX, entryY);
        changeDirection(maze.getWidth()-1, maze.getHeight()-1);
        maze.setMouse(mouse);
        imageView.setRotate(mouse.direction * 90);
        ((Pane)getNodeFromGridPane(pnGridMaze, maze.getExitX(), maze.getExitY())).getChildren().clear();
        ((Pane)getNodeFromGridPane(pnGridMaze, mouse.x, mouse.y)).getChildren().add(imageView);    
    }
    
    //Обработчик нажатия на кнопку "Пройти"
    public void btAutoGoButtonAction(ActionEvent actionEvent) {
        int ExitX = maze.getExitX();
        int ExitY = maze.getExitY();
        do {
            ((Pane)getNodeFromGridPane(pnGridMaze, mouse.x, mouse.y)).getChildren().clear();
            mouse.runRightHandAlg(maze.isFrontWall(), maze.isRightWall());
            imageView.setRotate(mouse.direction * 90);
            ((Pane)getNodeFromGridPane(pnGridMaze, mouse.x, mouse.y)).getChildren().add(imageView);   
        }
        while (!(mouse.x == ExitX && mouse.y == ExitY));
        btAutoGo.setDisable(true);
        btStart.setDisable(false);
    }
    
    //Обработчик нажатия на кнопку "Сделать шаг"
    public void btStepButtonAction(ActionEvent actionEvent) {
        int ExitX = maze.getExitX();
        int ExitY = maze.getExitY();
        ((Pane)getNodeFromGridPane(pnGridMaze, mouse.x, mouse.y)).getChildren().clear();
        if (rbAlgRight.isSelected()) {
            mouse.runRightHandAlg(maze.isFrontWall(), maze.isRightWall());
        }
        else {
            
        }
        imageView.setRotate(mouse.direction * 90);
        ((Pane)getNodeFromGridPane(pnGridMaze, mouse.x, mouse.y)).getChildren().add(imageView);
        if (mouse.x == ExitX && mouse.y == ExitY) {
            btStep.setDisable(true);
            btStart.setDisable(false);
        }
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
        Pane pane = (Pane) getNodeFromGridPane(pnGridMaze, i, j);
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
    
    //Изменяет направление мыши при появлении на старте
    public void changeDirection(int columns, int rows) {
        if (mouse.y == rows) {
            mouse.direction = 0;
        }
        else if (mouse.x == 0) {
            mouse.direction = 1;
        }
        else if (mouse.y == 0) {
            mouse.direction = 2;
        }
        else if (mouse.x == columns) {
            mouse.direction = 3;
        }   
    }
    
    //Возвращает объект панели по индексу
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    } 
}
