package com.example.pokemonfieldguideapp;

import android.os.Bundle;

import android.util.Log;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    String url = "https://pokeapi.co/api/v2/pokemon/";

    private JSONArray resultsArray;
    private ImageView pImage;
    private EditText searchPoke;
    private TextView pokeName;
    private TextView pokeID;
    private TextView pokeHP;
    private TextView pokeATK;
    private TextView pokeDEF;
    private TextView pokeSpATK;
    private TextView pokeSpDEF;
    private TextView pokeSPD;

    private String name = "DEFAULT";
    private int id;
    private String  hp;
    private String atk;
    private String def;
    private String specATK;
    private String specDEF;
    private String spd;
    private String spriteUrl = "Default";


    RequestQueue queue;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    void pokeFetcher() {
        String newUrl = url + searchPoke.getText();

        JsonObjectRequest pokeRequest = new JsonObjectRequest(Request.Method.GET, newUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(getContext(), "JSON Launched", Toast.LENGTH_LONG).show();
                            //Log.d("name", "name");
                            pokeParser(response);//call the parser function to parse the data from the json response


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> name = "Error:" + error.toString());//some sort of error display
        // Add the request to the RequestQueue.
        queue.add(pokeRequest);
    }

    void pokeParser(JSONObject response) throws JSONException {//this is the JSON parsing method, this needs to be custom made to match your JSON and data needs
        name = response.getString("name");//use getString to extract strings
        id = response.getInt("id");//use getInt to extract ints
        JSONObject sprites = response.getJSONObject("sprites");
        spriteUrl = sprites.getString("front_default");
        resultsArray = response.getJSONArray("stats");
        JSONObject statObject = resultsArray.getJSONObject(0);
        hp = String.valueOf(statObject.getInt("base_stat"));
        hp = "HP: " + hp;
        statObject = resultsArray.getJSONObject(1);
        atk = String.valueOf(statObject.getInt("base_stat"));
        atk = "Attack: " + atk;
        statObject = resultsArray.getJSONObject(2);
        def = String.valueOf(statObject.getInt("base_stat"));
        def = "Defense: " + def;
        statObject = resultsArray.getJSONObject(3);
        specATK = String.valueOf(statObject.getInt("base_stat"));
        specATK = "Sp. ATK: " + specATK;
        statObject = resultsArray.getJSONObject(4);
        specDEF = String.valueOf(statObject.getInt("base_stat"));
        specDEF = "Sp. DEF: " + specDEF;
        statObject = resultsArray.getJSONObject(5);
        spd = String.valueOf(statObject.getInt("base_stat"));
        spd = "Speed: " + spd;
        //Toast.makeText(getContext(), "JSON Launched", Toast.LENGTH_LONG).show();

        //sprites = response.getJSONObject("sprites");//get the object first..
        //spritesUrl = sprites.getString("front_default");//..then get the string from the object

        //abilitiesArray = response.getJSONArray("abilities");
        //for (int i = 0; i < abilitiesArray.length(); i++) {

            // store each object in JSONObject
            //JSONObject abilityObj = abilitiesArray.getJSONObject(i);

            //grab the nested object from the outer json object, mine is called "ability"
            //JSONObject abilityObjInner = abilityObj.getJSONObject("ability");

            //now we finally have access to get field value from JSONObject using get() method
            //abilitiesTxt = abilitiesTxt + (i + 1) + ":" + abilityObjInner.getString("name") + "\n";//ability name is a string
       // }
    }

    void pokeFetchSequencer(){
        pokeFetcher();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run(){
                getActivity().runOnUiThread(() -> uiUpdater());
            }
        }, 1000);
    }

    void uiUpdater() {
        pokeName.setText(name);
        pokeID.setText(String.valueOf(id));//must cast integer to string, setText only handles strings
        pokeHP.setText(String.valueOf(hp));
        pokeATK.setText(String.valueOf(atk));
        pokeDEF.setText(String.valueOf(def));
        pokeSpATK.setText(String.valueOf(specATK));
        pokeSpDEF.setText(String.valueOf(specDEF));
        pokeSPD.setText(String.valueOf(spd));
        Picasso.get()
                .load(spriteUrl)
                .placeholder(R.drawable._5d5cded00f3a3e8a98fb1eed568aa9f)
                .into(pImage);

    }


        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        pokeName = (TextView) view.findViewById(R.id.pName);
        searchPoke = (EditText) view.findViewById(R.id.search_pokemon);
        pokeID = (TextView) view.findViewById(R.id.pID);
        pokeHP = (TextView) view.findViewById(R.id.baseHP);
        pokeATK = (TextView) view.findViewById(R.id.baseATK);
        pokeDEF = (TextView) view.findViewById(R.id.baseDEF);
        pokeSpATK = (TextView) view.findViewById(R.id.spATK);
        pokeSpDEF = (TextView) view.findViewById(R.id.spDEF);
        pokeSPD = (TextView) view.findViewById(R.id.baseSPD);
        pImage = (ImageView) view.findViewById(R.id.pokeImage);
        Button btn = (Button) view.findViewById(R.id.search_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokeFetchSequencer();
            }
        });
        return view;
    }
}