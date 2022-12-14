package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.model.Jugador;
import cutas.gabriel.wordle.model.Palabra;
import io.realm.Realm;
import io.realm.RealmResults;

public class Ajustes extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("AJUSTES");
        Realm realm = Realm.getDefaultInstance();
        Button btnPalabras = (Button) findViewById(R.id.reiniciarPalabras);
        Button btnJugadores = (Button) findViewById(R.id.reiniciarJugadores);

        btnJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                realm.delete(Jugador.class);
                realm.commitTransaction();
                Toast error = Toast.makeText(getApplicationContext(),"BASE DE DATOS DE JUGADORES LIMPIADA CON ÉXITO", Toast.LENGTH_SHORT);
                error.show();
            }
        });

        btnPalabras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                realm.delete(Palabra.class);
                realm.commitTransaction();
                Toast error = Toast.makeText(getApplicationContext(),"BASE DE DATOS DE PALABRAS LIMPIADA CON ÉXITO", Toast.LENGTH_SHORT);
                error.show();
            }
        });
    }
}