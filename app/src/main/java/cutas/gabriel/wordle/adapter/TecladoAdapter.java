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
import cutas.gabriel.wordle.model.Letra;

public class TecladoAdapter extends RecyclerView.Adapter<TecladoAdapter.NoteHolder> {

    private List<Letra> listData;
    private OnItemClickListener itemListener;


    public TecladoAdapter(List<Letra> listData, OnItemClickListener listener) {
        this.itemListener = listener;
        this.listData = listData;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.telado_list,parent, false);
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

        TextView titulo;

        public NoteHolder(@NonNull  View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtView);


        }

        public void assignData(Letra letra, final OnItemClickListener onItemClickListener) {

            this.titulo.setText(letra.getCaracter());
            this.titulo.setTextColor(Color.parseColor(letra.getColor()));
            //this.titulo.setBackgroundColor(Color.parseColor();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(letra.getCaracter());
                }
            });
        }

    }
}

