package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.model.Jugador;
import io.realm.Realm;

public class DetallesJugador extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_jugador);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETALLES PARTIDA");
        Realm realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        Jugador jugador = realm.where(Jugador.class).equalTo("nombre",intent.getStringExtra("nombre")).findFirst();
        TextView nombreTxt = findViewById(R.id.txtNombreDetalles);
        TextView tiempoTxt = findViewById(R.id.txtTiempoDetalles);
        TextView intentosTxt = findViewById(R.id.txtIntentosDetalles);
        TextView puntosTxt = findViewById(R.id.txtPuntosDetalles);
        nombreTxt.setText(jugador.getNombre());
        tiempoTxt.setText("TIEMPO: " + jugador.getTiempo() + "S");
        intentosTxt.setText("INTENTOS: " + jugador.getIntentos());
        puntosTxt.setText("PUNTOS: " + jugador.getPuntos());

    }
}