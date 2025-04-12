package Collections;

import java.io.Serializable;

/**
 * Класс, представляющий расположение в трехмерном пространстве.
 * Содержит три поля: координату по оси X, координату по оси Y и координату по оси Z.
 * Поле Z не может быть равно null.
 */
public class Location implements Serializable {
    private long x;
    private double y;
    private Float z; //Поле не может быть null

    /**
     * Конструктор для создания объекта расположения.
     *
     *@param x координата по оси X (долгота)
     *@param y координата по оси Y (широта)
     *@param z координата по оси Z (высота), не может быть null
     */
    public Location(long x, double y, Float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Возвращает строковое представление расположения в формате "x,y,z".
     *
     *@return строковое представление объекта в виде "x,y,z"
     */
    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
