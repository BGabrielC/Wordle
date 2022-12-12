package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

import cutas.gabriel.wordle.R;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button btnJugar = (Button) findViewById(R.id.btnJugar);
        Button btnRanking = (Button) findViewById(R.id.btnRanking);
        Button btnAjustes = (Button) findViewById(R.id.btnAjustes);
        Intent Jugar = new Intent(PantallaInicio.this,Juego.class);
        Intent Ajustes = new Intent(PantallaInicio.this, Ajustes.class);
        Intent Ranking = new Intent(PantallaInicio.this,Ranking.class);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Jugar);
            }
        });


        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Ranking);
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Ajustes);
            }
        });
}
}