package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import cutas.gabriel.wordle.R;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button btnJugar = (Button) findViewById(R.id.btnJugar);
        Button btnReglas = (Button) findViewById(R.id.btnReglas);
        Button btnRanking = (Button) findViewById(R.id.btnRanking);
        Intent Jugar = new Intent(PantallaInicio.this,Juego.class);

        Intent Reglas = new Intent(PantallaInicio.this,Reglas.class);

        Intent Ranking = new Intent(PantallaInicio.this,Ranking.class);


        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Jugar);
            }
        });

        btnReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Reglas);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Ranking);
            }
        });
}
}