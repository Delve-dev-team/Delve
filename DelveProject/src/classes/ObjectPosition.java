package classes;

import java.util.Objects;

// this class is simply a class that holds the x,y position of a object on the map
public class ObjectPosition
{ 
    //private fields
    private int rowPosition;
    private int columnPosition;
    private Map map;

    public static ObjectPosition of( int rowPosition, int columnPosition, Map map)
    {
        //some code the see if these inputs are valid

        //create the Position
        return new ObjectPosition(rowPosition, columnPosition, map);
    }

    private ObjectPosition( int rowPosition, int columnPosition, Map map)
    {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
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

    @Override
    public String toString() {
        return "ObjectPosition{"+
                "rowPosition="+rowPosition+
                ", columnPosition="+columnPosition+
                '}';
    }
}
