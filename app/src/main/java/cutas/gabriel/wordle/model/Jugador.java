package cutas.gabriel.wordle.model;

import cutas.gabriel.wordle.app.MyApplication;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Jugador extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String nombre;
    private double tiempo;
    private int intentos;

    public Jugador(String nombre, double tiempo, int intentos) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.intentos = intentos;
        this.id = MyApplication.jugadorID.incrementAndGet();
    }

    public Jugador() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
