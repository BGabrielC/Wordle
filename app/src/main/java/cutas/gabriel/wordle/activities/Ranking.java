package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.adapter.OnItemClickListener;
import cutas.gabriel.wordle.adapter.RankingAdapter;
import cutas.gabriel.wordle.model.Jugador;
import cutas.gabriel.wordle.model.Palabra;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRanking);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Jugador> realmJugadores = realm.where(Jugador.class).sort("puntos", Sort. DESCENDING).findAll();
        Intent Detalles = new Intent(Ranking.this,DetallesJugador.class);
        TextView ayuda = findViewById(R.id.txtAyuda);

        if (realmJugadores.size() != 0){
            ayuda.setText("(Pulsa el nombre para mas detalles)");
        }else {
            ayuda.setText("(Por ahora no hay ningún registro)");
        }


        RankingAdapter rankingAdapter= new RankingAdapter(realmJugadores, new OnItemClickListener() {
            @Override
            public void onItemClick(String character) {
                Detalles.putExtra("nombre",character);
                startActivity(Detalles);
            }
        });
        recyclerView.setAdapter(rankingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

    }
}