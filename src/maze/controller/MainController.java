package maze.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;


public class MainController {
    @FXML
    private RadioButton rbAlgPrim; //переключатель "Прима"
    
    @FXML
    private RadioButton rbAlgRight; //переключатель "Правой руки"
    
    @FXML
    private RadioButton rbModeAuto; //переключатель "Автоматический"
    
    //Обработчик нажатия на кнопку "Сгенерировать"
    public void btGenerateButtonAction(ActionEvent actionEvent) {
        
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
