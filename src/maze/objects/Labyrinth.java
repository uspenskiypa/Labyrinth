package maze.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Labyrinth {
    private Location[][] maze; //двумерный массив локаций
    private static final int[] dx = {-1, 0, 1, 0}; //изменение координат по х
    private static final int[] dy = {0, -1, 0, 1}; //изменение координат по y
    public  enum Attribute {INSIDE, OUTSIDE, BORDER}; //статус локаций
    private ArrayList<Location> borderList = new ArrayList<Location>(); //список с локациями, статус  "BORDER"
    private ArrayList<Location> insideList = new ArrayList<Location>(); //список с локациями, статус  "INSIDE"
    
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
    //статус - OUTSIDE. Случайной локации присваиваем статус INSIDE,
    //ее соседям - BORDER. Пока существуют локации со статусом BORDER,
    //случайной локации присваиваем статус INSIDE, ее соседям - BORDER,
    //если они OUTSIDE, разрушаем стену между текущей локацией и случайной
    //соседней локацией со статусом INSIDE.
    public void runPrimAlg(int width, int height) {
        int count = 0; //случайное число для выбора локации
        Random rn = new Random(); //объект класса Random
        Location currentLocation; //текущая локация
        Location randomLocation; //соседняя локация
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
    
    //Алгоритм Краскала. Все стены лабиринта записываются в список. Стена 
    //представляет собой массив из двух локаций, между которыми находится
    //эта стена. Список со стенами случайно перетасовывается, после чего каждая
    //стена проверяется: если между локациями по обе стороны от этой стены 
    //нет прохода, то стена разрушается.
    public void runKruskalAlg(int width, int height) {
        LinkedList<Location[]> wallList = new LinkedList<Location[]>(); //список со всеми стенами
        for (int i = 0; i < width; i++) { //Добавляем горизонтальные стены
            for (int j = 0; j < height-1; j++) {
                Location [] temp = {maze[i][j], maze[i][j+1]};
                wallList.add(temp);
            }
        }
        for (int i = 0; i < width-1; i++) { //Добавляем вертикальные стены
            for (int j = 0; j < height; j++) {
                Location [] temp = {maze[i][j], maze[i+1][j]};
                wallList.add(temp);
            }
        }
        java.util.Collections.shuffle(wallList); //перетасовка локаций
        while(!wallList.isEmpty()) {
            Location [] temp = wallList.pollFirst();
            if (!foundWay(temp[0], temp[1], width, height)) {
                breakWall(temp[0], temp[1]);   
            }
        }
    }

    //Разрушает стену между двумя локациями
    private void breakWall(Location firstLocation, Location secondLocation) {
        int dX = secondLocation.x - firstLocation.x;
        int dY = secondLocation.y - firstLocation.y;
        if (dX == 0) {
            if (dY == 1) {
                firstLocation.downWall = false;
            }
            else {
                secondLocation.downWall = false;
            }
        }
        else {
            if (dX == 1) {
                firstLocation.rightWall = false;
            }
            else {
                secondLocation.rightWall = false;
            }
        }
    }
    
    //Изменяет статус соседних локаций на BORDER, заполняет списки с локациями
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
    
    //Ищет путь между локациями. Если путь найден вернет true, иначе - false.
    //Поиск осуществляется методом "поглощения". От локации firstLocation 
    //начинается движение во все стороны, если на пути нет стены, то очередная 
    //локация добавляется в список firstList, пока не будет найдена искомая 
    //локация desiredLocation. Если все возможные локации пройдены, а путь 
    //не найдет, то такого пути нет.
    private boolean foundWay(Location firstLocation, Location desiredLocation, int width, int height) {
        boolean isWall, deadlock; //наличие стены и тупика
        int xc, yc; //координаты соседних локаций
        Location currentLocation; //текущая локация
        LinkedList<Location> firstList = new LinkedList<Location>();
        firstList.add(firstLocation);
        do {
            deadlock = true; //предполагаем, что пути нет
            for (int i = 0; i < firstList.size(); i++) {
                currentLocation = firstList.get(i);
                for (int j = 0; j < 4; j++) { //движемся во все стороны
                    xc = currentLocation.x + dx[j];
                    yc = currentLocation.y + dy[j];
                    if ((xc >= 0) && (yc >= 0) && (xc < width) && (yc < height)) {
                        if (!firstList.contains(maze[xc][yc])) {
                            isWall = checkWall(currentLocation, maze[xc][yc]);
                            if (!isWall) {
                                if (maze[xc][yc] == desiredLocation) {
                                    return true;
                                } else {
                                    deadlock = false;
                                    firstList.add(maze[xc][yc]);
                                }
                            }
                        }
                    }
                }
            }
        } while (!deadlock);
        return false;
    }
    
    //Проверяет наличие стены между локациями. Если стена существует вернет true, иначе false.
    private boolean checkWall(Location firstLocation, Location secondLocation) {
        int dX = secondLocation.x - firstLocation.x;
        int dY = secondLocation.y - firstLocation.y;
        if (dX == 0) {
            if (dY == 1) {
                if (firstLocation.downWall) {
                    return true;
                } 
            }
            else {  
                if (secondLocation.downWall) {
                    return true;
                } 
            }
        }
        else {
            if (dX == 1) {
                if (firstLocation.rightWall) {
                    return true;
                }
            }  
            else {
                if (secondLocation.rightWall) {   
                    return true;
                }
            }  
        }
        return false;
    }
}
