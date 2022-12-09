package org.iesch.practica1.proyectofinal1aevmrubios.modelo;

public class Poke {
    private int base_attack;
    private int base_defense;
    private int base_stamina;
    private String form;
    private int pokemon_id;
    private String pokemon_name;

    public int getBase_attack() {
        return base_attack;
    }

    public void setBase_attack(int base_attack) {
        this.base_attack = base_attack;
    }

    public int getBase_defense() {
        return base_defense;
    }

    public void setBase_defense(int base_defense) {
        this.base_defense = base_defense;
    }

    public int getBase_stamina() {
        return base_stamina;
    }

    public void setBase_stamina(int base_stamina) {
        this.base_stamina = base_stamina;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getPokemon_id() {
        return pokemon_id;
    }

    public void setPokemon_id(int pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public String getPokemon_name() {
        return pokemon_name;
    }

    public void setPokemon_name(String pokemon_name) {
        this.pokemon_name = pokemon_name;
    }

    public Poke() {
    }

    public Poke(int base_attack, int base_defense, int base_stamina, String form, int pokemon_id, String pokemon_name) {
        this.base_attack = base_attack;
        this.base_defense = base_defense;
        this.base_stamina = base_stamina;
        this.form = form;
        this.pokemon_id = pokemon_id;
        this.pokemon_name = pokemon_name;
    }
}
