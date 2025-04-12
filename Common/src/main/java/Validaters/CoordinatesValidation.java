//package Validaters;
//
//import Collections.Coordinates;
//import Tools.Validation;
//
//public class CoordinatesValidation implements Validation {
//    private final Coordinates coordinates;
//    private final int x;
//    private final double y;
//
//    public CoordinatesValidation(Coordinates coordinates, int x, double y) {
//        this.x = x;
//        this.y = y;
//        this.coordinates = new Coordinates(x, y);
//    }
//
//    @Override
//    public boolean validate() { return coordinates != null; }
//
//    @Override
//    public String getErrorMessage() {
//        return "Ошибка в Coordinates";
//    }
//}
