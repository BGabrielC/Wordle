package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import cutas.gabriel.wordle.R;

public class Derrota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derrota);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button btnVolver = (Button) findViewById(R.id.btnVolverMenuPrincipal);

        Intent volver = new Intent(Derrota.this,PantallaInicio.class);
        Intent intent = getIntent();
        String palabra = intent.getStringExtra("palabra");
        TextView solucion = findViewById(R.id.txtPalabra);
        solucion.setText("LA PALABRA ERA " + palabra);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(volver);
            }
        });

    }
}