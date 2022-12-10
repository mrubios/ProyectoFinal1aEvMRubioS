package org.iesch.practica1.proyectofinal1aevmrubios.api;

import org.iesch.practica1.proyectofinal1aevmrubios.modelo.Poke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeapiService {
    @GET("/api/v1/pokemon_stats.json")
    Call<List<Poke>> obtenerListaPoke();
}
