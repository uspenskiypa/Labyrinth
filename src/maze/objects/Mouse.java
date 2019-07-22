package maze.objects;

import java.util.LinkedList;

public class Mouse {
    public int x, y; //координаты мыши
    public int direction; //направление движения
    private LinkedList<Location> firstList; //список с координатоми локаций, пройденных единожды
    private LinkedList<Location> secondList; //список с координатоми локаций, пройденных дважды
    
    public Mouse(int x, int y) {
        this.x = x;
        this.y = y;
        firstList = new LinkedList<Location>();
        secondList = new LinkedList<Location>();
    }
    
    //Алгоритм "правой руки".
    public void runRightHandAlg(boolean isFrontWall, boolean isRightWall) {
        if (isRightWall) {
            if (isFrontWall) {
                leftRotate();
            }
            else {
                toStep();
            }
        }
        else {
            rightRotate();
            toStep();
        }
    }
    
    //Алгоритм "правой руки".
    public void runTremoAlg() {
        
    }
    
    //передвигает мышь на шаг вперед
    public void toStep() {
        switch (direction) {
            case 0: --y; break;
            case 1: ++x; break;
            case 2: ++y; break;
            case 3: --x; break;
        }
    }
    
    //разварачивает мышь на 90 градусов вправо
    public void rightRotate() {
        ++direction;
        if (direction > 3) {
            direction = 0;
        }
    }
    
    //разварачивает мышь на 90 градусов влево
    public void leftRotate() {
        --direction;
        if (direction < 0) {
            direction = 3;
        }
    }
    
    //разварачивает мышь на перекрестке
    public void smartRotate() {
        
    }
}
