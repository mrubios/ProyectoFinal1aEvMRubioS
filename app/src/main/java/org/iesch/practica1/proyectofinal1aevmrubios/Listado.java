package org.iesch.practica1.proyectofinal1aevmrubios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import org.iesch.practica1.proyectofinal1aevmrubios.adaptador.ListaPokeAdapter;
import org.iesch.practica1.proyectofinal1aevmrubios.api.PokeapiService;
import org.iesch.practica1.proyectofinal1aevmrubios.modelo.Poke;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listado extends AppCompatActivity{

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPokeAdapter listaPokeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        listaPokeAdapter = new ListaPokeAdapter(this);
        recyclerView.setAdapter(listaPokeAdapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                        Log.i("POKEMON","Llegamos al Final");
                        obtenerDatos();
                    }



            }
        });

        retrofit = new Retrofit.Builder().baseUrl("https://pogoapi.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        obtenerDatos();
    }


    public void obtenerDatos() {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<List<Poke>> pizzaRespuestaCall = service.obtenerListaPoke();

        pizzaRespuestaCall.enqueue(new Callback<List<Poke>>() {
            @Override
            public void onResponse(Call<List<Poke>> call, Response<List<Poke>> response) {

                if (response.isSuccessful()) {
                    listaPokeAdapter.anadirPoke(response.body());
                } else {
                    Log.i("Poke", "onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<List<Poke>> call, Throwable t) {
                Log.i("Poke", "onFailure: " + t.getMessage());
            }
        });
    }

}