package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.example.user.myapplication.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity implements OnPokemonSelectedChangedListener, AdapterView.OnItemClickListener {

    PokemonlistAdapter arrayAdapter;
    public static final int detailActivityResultCode = 1;
    public static final String ownedPokemonInfoKey = "parcelable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        Intent srcIntent = getIntent();
        int selectedPokemonIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey,0);


        OwnedPokemonInfoDataManager dataManager = new OwnedPokemonInfoDataManager(this);
        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos = dataManager.getOwnedPokemonInfos();
        ArrayList<OwnedPokemonInfo> initPokemonInfos = dataManager.getInitPokemonInfos();

        OwnedPokemonInfo selectedPokemonInfo = initPokemonInfos.get(selectedPokemonIndex);

        ownedPokemonInfos.add(0,selectedPokemonInfo);

//        ArrayList<String> pokemonNames = new ArrayList<>();
//        for(OwnedPokemonInfo ownedPokemonInfo : ownedPokemonInfos){
//            pokemonNames.add(ownedPokemonInfo.name);
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pokemonNames);


        arrayAdapter = new PokemonlistAdapter(this, R.layout.row_view_of_pokemon_list, ownedPokemonInfos);
        arrayAdapter.listener = this;
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(arrayAdapter.selectedPokemonInfos.isEmpty()){
            return false;
        }else{
            getMenuInflater().inflate(R.menu.selected_pokemon_list_action_bar, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemonInfos){
                arrayAdapter.remove(ownedPokemonInfo);
            }
            arrayAdapter.selectedPokemonInfos.clear();
            invalidateOptionsMenu();
            return true;
        } else if (itemId == R.id.action_setting) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedChanged(OwnedPokemonInfo data) {
        invalidateOptionsMenu();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OwnedPokemonInfo data = arrayAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(PokemonListActivity.this,DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey,data);
        startActivityForResult(intent,detailActivityResultCode);

    }
}
