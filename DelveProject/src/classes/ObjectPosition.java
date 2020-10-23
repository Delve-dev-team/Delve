package classes;

import java.util.Objects;

// this class is simply a class that holds the x,y position of a object on the map
public class ObjectPosition
{
    //private fields
    private int columnPosition;
    private int rowPosition;
    private Map map;

    public static ObjectPosition of(int columnPosition, int rowPosition, Map map)
    {
        //some code the see if these inputs are valid

        //create the Position
        return new ObjectPosition(columnPosition,rowPosition,map);
    }

    private ObjectPosition(int columnPosition, int rowPosition, Map map)
    {
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ObjectPosition that = (ObjectPosition) o;
        return columnPosition == that.columnPosition &&
                rowPosition == that.rowPosition &&
                map.equals(that.map);
    }

    public int getColumnPosition()
    {
        return columnPosition;
    }

    public int getRowPosition()
    {
        return rowPosition;
    }
}
