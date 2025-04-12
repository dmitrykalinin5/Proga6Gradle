package Collections;

import java.io.Serializable;

/**
 * Класс, представляющий координаты в двухмерном пространстве.
 * Содержит два поля: целочисленную координату по оси X и вещественную координату по оси Y.
 */
public class Coordinates implements Serializable {
    private final int x;
    private final double y;

    /**
     * Конструктор для создания объекта координат.
     *@param x координата по оси X (целое число)
     *@param y координата по оси Y (вещественное число)
     */
    public Coordinates(int x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получить значение координаты по оси X.
     *@return координата по оси X
     */
    public int getX() {
        return x;
    }

    /**
     * Получить значение координаты по оси Y.
     *@return координата по оси Y
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает строковое представление координат в формате "x, y".
     *@return строковое представление объекта в виде "x, y"
     */
    @Override
    public String toString() {
        return x + ", " + y;
    }
}
