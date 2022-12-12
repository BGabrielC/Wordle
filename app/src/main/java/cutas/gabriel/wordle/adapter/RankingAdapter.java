package cutas.gabriel.wordle.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.model.Jugador;
import cutas.gabriel.wordle.model.Letra;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.NoteHolder> {

    private List<Jugador> listData;
    private OnItemClickListener itemListener;


    public RankingAdapter(List<Jugador> listData, OnItemClickListener listener) {
        this.itemListener = listener;
        this.listData = listData;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rankig_list,parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, final int position) {
        holder.assignData(listData.get(position), itemListener);
    }

    @Override
    public int getItemCount() {

        return listData.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {

        TextView txtJugador;

        public NoteHolder(@NonNull  View itemView) {
            super(itemView);
            txtJugador = itemView.findViewById(R.id.txtNombreJugador);


        }

        public void assignData(Jugador jugador, final OnItemClickListener onItemClickListener) {

            this.txtJugador.setText(jugador.getNombre());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(jugador.getNombre());
                }
            });
        }

    }
}

