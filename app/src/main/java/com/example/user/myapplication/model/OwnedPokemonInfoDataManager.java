package com.example.user.myapplication.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 2016/9/5.
 */
public class OwnedPokemonInfoDataManager {


    Context mContext;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;


    public OwnedPokemonInfoDataManager(Context context){
        mContext = context;
    }


    public void loadListViewData(){
        ownedPokemonInfos = new ArrayList<>();
        BufferedReader reader;
        String line = null;
        String[] dataFields = null;


        //取得Asset資料夾底下的檔案
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("pokemon_data.csv")));

            while((line = reader.readLine())!=null){
                dataFields = line.split(",");
                //初始ownedPokemonInfos arrayList 資料
                ownedPokemonInfos.add(constructPokemonInfo((dataFields)));
            }

            reader.close();





        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    OwnedPokemonInfo constructPokemonInfo(String[] dataFields){
        OwnedPokemonInfo ownedPokemonInfo = new OwnedPokemonInfo();
        ownedPokemonInfo.pokemonId = Integer.valueOf(dataFields[0]);
        ownedPokemonInfo.name = dataFields[2];
        ownedPokemonInfo.level = Integer.valueOf(dataFields[3]);
        ownedPokemonInfo.currentHP = Integer.valueOf(dataFields[4]);
        ownedPokemonInfo.maxHP = Integer.valueOf(dataFields[5]);
        ownedPokemonInfo.type1Index = Integer.valueOf(dataFields[6]);
        ownedPokemonInfo.type2Index = Integer.valueOf(dataFields[7]);
        String [] skills = new String[OwnedPokemonInfo.maxNumSkills];

        for(int i = 8;i<dataFields.length;i++){
            skills[i-8] = dataFields[i];
        }

        ownedPokemonInfo.skills = skills;

        return ownedPokemonInfo;


    }


    public ArrayList<OwnedPokemonInfo> getOwnedPokemonInfos(){


        return ownedPokemonInfos;

    }





}
