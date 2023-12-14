package com.example.pokemonfieldguideapp;

public class Pokemon {
    private String pokeName;
    private String pokeID;

    public Pokemon(String newName,String newID) {
        pokeName = newName;
        pokeID = newID;
    }

    public String getID() {
        return pokeID;
    }


    public String getName() {
        return pokeName;
    }

}
