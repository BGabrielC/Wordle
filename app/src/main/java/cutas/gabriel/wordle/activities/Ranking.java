package cutas.gabriel.wordle.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    RecyclerView recyclerView;
    Realm realm;
    RealmResults<Jugador> realmJugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RANKING");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRanking);
        realm = Realm.getDefaultInstance();
        realmJugadores = realm.where(Jugador.class).sort("puntos", Sort. DESCENDING).findAll();

        RankingAdapter rankingAdapter= new RankingAdapter(realmJugadores, new OnItemClickListener() {
            @Override
            public void onItemClick(String character) {
                Toast toast2 = Toast.makeText(getApplicationContext(),character, Toast.LENGTH_SHORT);
                toast2.show();
            }
        });
        recyclerView.setAdapter(rankingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

    }
}