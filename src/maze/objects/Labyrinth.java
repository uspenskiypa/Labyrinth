package maze.objects;

import java.util.ArrayList;
import java.util.Random;

public class Labyrinth {
    private ArrayList<Location> borderList = new ArrayList<Location>(); //список с локациями, статус  "BORDER"
    private ArrayList<Location> insideList = new ArrayList<Location>(); //список с локациями, статус  "INSIDE"
    private Location[][] maze; //двумерный массив локаций
    private Location currentLocation; //текущая локация
    private Location randomLocation; //соседняя локация
    private Random rn = new Random(); //объект класса Random
    public  enum Attribute {INSIDE, OUTSIDE, BORDER}; //статус локаций
    private static final int[] dx = {-1, 0, 1, 0}; //изменение координат по х
    private static final int[] dy = {0, -1, 0, 1}; //изменение координат по y
    
    //Конструктор. Создание массива maze и его заполнение локациями,
    //присвоение локациям координат расположения в массиве. 
    public Labyrinth(int columns, int rows) {
        maze = new Location[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                maze[i][j] = new Location();
                maze[i][j].x = i;
                maze[i][j].y = j;
            }
        }
    }
    
    public Location getLocation(int columns, int rows) {
        return maze[columns][rows];
    }
    
    //Алгоритм Прима. Локации имеют статус (атрибут). изначально их  
    //статус - UTSIDE. Случайной локации присваиваем статус INSIDE,
    //ее соседям - BORDER. Пока существуют локации со статусом BORDER,
    //случайной локации присваиваем статус INSIDE, ее соседям - BORDER,
    //если они OUTSIDE, разрушаем стену между текущей локацией и случайной
    //соседней локацией со статусом INSIDE.
    public void runPrimAlg(int width, int height) {
        int count = 0; //случайное число для выбора локации
        currentLocation = maze[rn.nextInt(width)][rn.nextInt(height)];
        currentLocation.attribute = Attribute.INSIDE;
        changeLocationStatus(currentLocation, width, height);        
        while(!borderList.isEmpty()) {
            count = rn.nextInt(borderList.size());    
            currentLocation = borderList.get(count);
            currentLocation.attribute = Attribute.INSIDE;
            borderList.remove(count);
            changeLocationStatus(currentLocation, width, height);
            count = rn.nextInt(insideList.size());
            randomLocation = insideList.get(count);
            breakWall(currentLocation, randomLocation);
            insideList.clear(); 
        }
    }
    
    //разрушение стены между двумя локациями
    private void breakWall(Location currentLocation, Location randomLocation) {
        int dX = randomLocation.x - currentLocation.x;
        int dY = randomLocation.y - currentLocation.y;
        if (dX == 0) {
            if (dY == 1) {
                currentLocation.downWall = false;
            }
            else {
                randomLocation.downWall = false;
            }
        }
        else {
            if (dX == 1) {
                currentLocation.rightWall = false;
            }
            else {
                randomLocation.rightWall = false;
            }
        }
    }
    
    //изменение статуса соседних локаций на BORDER, заполнение списков с локациями
    private void changeLocationStatus(Location currentLocation, int width, int height) {
        int xc, yc; //координаты соседних локаций
        for (int i = 0; i < 4; i++) {
            xc = currentLocation.x + dx[i];
            yc = currentLocation.y + dy[i];
            if ((xc >= 0) && (yc >= 0) && (xc < width) && (yc < height)) {
                if (maze[xc][yc].attribute == Attribute.OUTSIDE) {
                    maze[xc][yc].attribute = Attribute.BORDER;
                    borderList.add(maze[xc][yc]);
                }
                if (maze[xc][yc].attribute == Attribute.INSIDE) {
                    insideList.add(maze[xc][yc]);
                }
            }
        }
    }
}
