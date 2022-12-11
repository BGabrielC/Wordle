package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.model.Jugador;
import cutas.gabriel.wordle.model.Palabra;
import io.realm.Realm;
import io.realm.RealmResults;

public class Victoria extends AppCompatActivity {

    Realm realm;
    RealmResults<Jugador> realmJugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victoria);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent intent = getIntent();
        double Tiempo = intent.getExtras().getDouble("tiempo");
        int numCaracteres = intent.getExtras().getInt("numCaracteres");
        int intentos = numCaracteres / 5;
        int puntuacion = (5 - intentos) * 3543 - (int) (Tiempo + 2349);
        TextView txtTiempo = findViewById(R.id.txtTiempo);
        TextView txtPuntos = findViewById(R.id.txtPuntuacion);
        TextView txtIntentos = findViewById(R.id.txtIntentos);
        txtTiempo.setText("  " + (int) Tiempo);
        txtPuntos.setText("  " + puntuacion);
        txtIntentos.setText("  " +intentos);
        realm = Realm.getDefaultInstance();
        realmJugadores = realm.where(Jugador.class).findAll();
    }
}