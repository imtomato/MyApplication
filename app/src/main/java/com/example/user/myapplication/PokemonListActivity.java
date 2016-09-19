package com.example.user.myapplication;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.example.user.myapplication.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends CustomizedActivity implements OnPokemonSelectedChangedListener
        , AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    PokemonlistAdapter arrayAdapter;
    AlertDialog deleteDialertDialog;
    public static final int detailActivityResultCode = 1;
    public static final String ownedPokemonInfoKey = "parcelable";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activityName = this.getClass().getSimpleName();
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



        arrayAdapter = new PokemonlistAdapter(this, R.layout.row_view_of_pokemon_list, ownedPokemonInfos);
        arrayAdapter.listener = this;
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        //initial alertdialog
        deleteDialertDialog = new AlertDialog
                .Builder(this).setTitle("警告!")
                .setMessage("你確定要丟棄這些神奇寶貝嗎?")
                .setPositiveButton("確認",this)
                .setNegativeButton("取消",this)
                .create();


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


    public void deleteSelcetedPokemon(){
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemonInfos){
            arrayAdapter.remove(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemonInfos.clear();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            deleteDialertDialog.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityResultCode){
            if(resultCode == DetailActivity.removeFromList){
                OwnedPokemonInfo mData = arrayAdapter.getItemWithNames(data.getStringExtra(OwnedPokemonInfo.nameKey));
                arrayAdapter.remove(mData);
                return;
            }else if(resultCode == DetailActivity.levelUp){


            }
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(dialog.equals(deleteDialertDialog)) {
           if(which == AlertDialog.BUTTON_POSITIVE){
               deleteSelcetedPokemon();
           }else if(which == AlertDialog.BUTTON_NEGATIVE){
               Toast.makeText(this,"取消丟棄",Toast.LENGTH_SHORT)
                       .show();
           }




        }
    }
}
