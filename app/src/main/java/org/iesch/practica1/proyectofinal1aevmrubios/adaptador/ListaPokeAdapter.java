package org.iesch.practica1.proyectofinal1aevmrubios.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.iesch.practica1.proyectofinal1aevmrubios.R;
import org.iesch.practica1.proyectofinal1aevmrubios.modelo.Poke;

import java.util.ArrayList;
import java.util.List;

public class ListaPokeAdapter extends RecyclerView.Adapter<ListaPokeAdapter.ViewHolder> {

    private List<Poke> dataset;
    private Context context;

    public ListaPokeAdapter(Context context){
        this.context=context;
        this.dataset = new ArrayList<>();
    }


    @NonNull
    @Override
    public ListaPokeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poke, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPokeAdapter.ViewHolder holder, int position) {
        Poke p = dataset.get(position);
        holder.nombre.setText(p.getPokemon_name());
        holder.id.setText(String.valueOf(p.getPokemon_id()));
        holder.forma.setText(p.getForm());
        holder.ataque.setText(String.valueOf(p.getBase_attack()));
        holder.defensa.setText(String.valueOf(p.getBase_defense()));
        holder.stamina.setText(String.valueOf(p.getBase_stamina()));


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void anadirPoke(List<Poke> listaPoke) {
        this.dataset.addAll(listaPoke);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, id, stamina, defensa, ataque, forma;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            id = itemView.findViewById(R.id.poke_id);
            stamina = itemView.findViewById(R.id.stamina);
            defensa = itemView.findViewById(R.id.defensa);
            ataque = itemView.findViewById(R.id.ataque);
            forma = itemView.findViewById(R.id.forma);
        }
    }
}
