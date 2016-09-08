package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.example.user.myapplication.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonInfoDataManager dataManager = new OwnedPokemonInfoDataManager(this);

        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos = dataManager.getOwnedPokemonInfos();



//        ArrayList<String> pokemonNames = new ArrayList<>();
//        for(OwnedPokemonInfo ownedPokemonInfo : ownedPokemonInfos){
//            pokemonNames.add(ownedPokemonInfo.name);
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pokemonNames);



        ArrayAdapter arrayAdapter = new PokemonlistAdapter(this,R.layout.row_view_of_pokemon_list,ownedPokemonInfos);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected_pokemon_list_action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete){
            //TODO
            return true;
        }else if (itemId == R.id.action_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
