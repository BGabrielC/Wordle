package cutas.gabriel.wordle.model;

import cutas.gabriel.wordle.app.MyApplication;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Palabra extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String palabra;

    public Palabra(String palabra) {
        this.palabra = palabra;
        this.id = MyApplication.palabraID.incrementAndGet();
    }

    public Palabra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}
