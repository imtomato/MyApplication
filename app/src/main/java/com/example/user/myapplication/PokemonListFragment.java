package com.example.user.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by user on 2016/9/22.
 */
public class PokemonListFragment extends Fragment implements OnPokemonSelectedChangedListener
        , AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

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

        Intent srcIntent = getActivity().getIntent();
        int selectedPokemonIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey, 0);


        OwnedPokemonInfoDataManager dataManager = new OwnedPokemonInfoDataManager(getActivity());
        dataManager.loadListViewData();

        ownedPokemonInfos = dataManager.getOwnedPokemonInfos();
        ArrayList<OwnedPokemonInfo> initPokemonInfos = dataManager.getInitPokemonInfos();

        OwnedPokemonInfo selectedPokemonInfo = initPokemonInfos.get(selectedPokemonIndex);

        ownedPokemonInfos.add(0, selectedPokemonInfo);


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
            arrayAdapter.remove(ownedPokemonInfo);
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
        startActivityForResult(intent,detailActivityResultCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Toast.makeText(getActivity(), "取消丟棄", Toast.LENGTH_SHORT)
                        .show();
            }




        }
    }
}
