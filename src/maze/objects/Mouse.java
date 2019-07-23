package maze.objects;

public class Mouse {
    public int x, y; //координаты мыши
    public int direction; //направление движения
    
    public Mouse(int x, int y) {
        this.x = x;
        this.y = y;
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
}
