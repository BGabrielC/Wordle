package cutas.gabriel.wordle.model;

public class Letra{

    private String color, caracter;

    public Letra(String caracter, String color){
        this(caracter);
        this.color = color;

    }
    public Letra(String caracter){
        this.caracter = caracter;
        this.color = "#000000";
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }
}
