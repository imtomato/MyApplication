package com.example.user.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.example.user.myapplication.model.OwnedPokemonInfoDataManager;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/9/22.
 */
public class PokemonListFragment extends Fragment implements OnPokemonSelectedChangedListener
        , AdapterView.OnItemClickListener, DialogInterface.OnClickListener, FindCallback<OwnedPokemonInfo>{


    public final static String recordIsInDBKey = "recordIsInDB";
    public static final int detailActivityResultCode = 1;
    public static final String ownedPokemonInfoKey = "parcelable";
    PokemonlistAdapter arrayAdapter;
    AlertDialog deleteDialertDialog;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;
    View fragmentView;


    public static PokemonListFragment newInstance() {

        Bundle args = new Bundle();

        PokemonListFragment fragment = new PokemonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownedPokemonInfos = new ArrayList<>();
        prepareListViewData();


    }

    public void loadFromCSV(){
        Intent srcIntent = getActivity().getIntent();
        int selectedPokemonIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey, 0);


        OwnedPokemonInfoDataManager dataManager = new OwnedPokemonInfoDataManager(getActivity());
        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> temp = dataManager.getOwnedPokemonInfos();
        for(OwnedPokemonInfo ownedPokemonInfo : temp) {
            ownedPokemonInfos.add(ownedPokemonInfo);
        }


        ArrayList<OwnedPokemonInfo> initPokemonInfos = dataManager.getInitPokemonInfos();

        OwnedPokemonInfo selectedPokemonInfo = initPokemonInfos.get(selectedPokemonIndex);

        ownedPokemonInfos.add(0, selectedPokemonInfo);
    }




    public void prepareListViewData(){

        SharedPreferences preferences = getActivity()
                .getSharedPreferences(PokemonListFragment.class.getSimpleName(), Context.MODE_PRIVATE);

        boolean recordInDB = preferences.getBoolean(recordIsInDBKey, false);
        if(!recordInDB){ // init DB
            loadFromCSV();
            OwnedPokemonInfo.initTable(ownedPokemonInfos);
            preferences.edit().putBoolean(recordIsInDBKey,true).commit();
        }else{ //read from DB
            OwnedPokemonInfo.getQuery().fromPin(OwnedPokemonInfo.localDBTableName) //read from localDB
                    .findInBackground(this);

            OwnedPokemonInfo.getQuery().findInBackground(this); //default read data from cloud DB


        }




    }


    @Override
    public void done(List<OwnedPokemonInfo> objects, ParseException e) {
        if(e == null){
            ownedPokemonInfos.clear();
            ownedPokemonInfos.addAll(objects);
            if(arrayAdapter != null){
                arrayAdapter.notifyDataSetChanged();
            }

        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_pokemon_list, null);
            arrayAdapter = new PokemonlistAdapter(getActivity(), R.layout.row_view_of_pokemon_list, ownedPokemonInfos);
            arrayAdapter.listener = this;
            ListView listView = (ListView) fragmentView.findViewById(R.id.listView);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(this);

            //initial alertdialog
            deleteDialertDialog = new AlertDialog
                    .Builder(getActivity()).setTitle("警告!")
                    .setMessage("你確定要丟棄這些神奇寶貝嗎?")
                    .setPositiveButton("確認", this)
                    .setNegativeButton("取消", this)
                    .create();

            //呼叫CreateOptionMenu
            setHasOptionsMenu(true);
            //確保Menu有顯示
            setMenuVisibility(true);

        }
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!arrayAdapter.selectedPokemonInfos.isEmpty()){
            inflater.inflate(R.menu.selected_pokemon_list_action_bar, menu);
        }
    }

    public void deleteSelcetedPokemon(){
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemonInfos){
            removePokemonInfo(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemonInfos.clear();
        getActivity().invalidateOptionsMenu();
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
        getActivity().invalidateOptionsMenu();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OwnedPokemonInfo data = arrayAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(),DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey,data);
        startActivityForResult(intent, detailActivityResultCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityResultCode){
            if(resultCode == DetailActivity.removeFromList){
                OwnedPokemonInfo mData = arrayAdapter.getItemWithNames(data.getStringExtra(OwnedPokemonInfo.nameKey));
               removePokemonInfo(mData);
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
                Toast.makeText(getActivity(), "取消丟棄", Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        OwnedPokemonInfo.saveToDB(ownedPokemonInfos);

    }


    public void removePokemonInfo(OwnedPokemonInfo ownedPokemonInfo){
        if(arrayAdapter != null){
            arrayAdapter.remove(ownedPokemonInfo);

            //remove from DB
            ownedPokemonInfo.unpinInBackground(OwnedPokemonInfo.localDBTableName);
            //remove from cloud DB
            ownedPokemonInfo.deleteEventually();


        }


    }









}
