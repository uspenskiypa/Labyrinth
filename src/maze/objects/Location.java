package maze.objects;

import maze.objects.Labyrinth.Attribute;

public class Location {
    public boolean rightWall; //наличие стены справа
    public boolean downWall; //наличие стены снизу
    public Attribute attribute; //атрибут (статус)
    public int x, y; //координаты локации

    public Location() {
        rightWall = true;
        downWall = true;
        attribute = Attribute.OUTSIDE;
    }
}
