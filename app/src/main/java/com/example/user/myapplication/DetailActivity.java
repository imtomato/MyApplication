package com.example.user.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;



public class DetailActivity extends AppCompatActivity {

    Resources mRes;
    String packageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRes = getResources();
        packageName = getPackageName();


        Intent intent = getIntent();
        OwnedPokemonInfo ownedPokemonInfo = intent.getParcelableExtra(PokemonListActivity.ownedPokemonInfoKey);

        setView(ownedPokemonInfo);


    }

    public void setView(OwnedPokemonInfo data) {

        ImageView detailImg = (ImageView) findViewById(R.id.detail_appearance_img);
        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView levelText = (TextView) findViewById(R.id.level_text);
        TextView currentHP = (TextView) findViewById(R.id.currentHP_text);
        TextView maxHP = (TextView) findViewById(R.id.maxHP_text);
        TextView type1Text = (TextView) findViewById(R.id.type_1_text);
        TextView type2Text = (TextView) findViewById(R.id.type_2_text);
        TextView[] skillsText = new TextView[OwnedPokemonInfo.maxNumSkills];
        for (int i = 0; i < OwnedPokemonInfo.maxNumSkills; i++) {
            int skillTextId = mRes.getIdentifier(String.format("skill_%d_text", i + 1), "id", packageName);
            skillsText[i] = (TextView) findViewById(skillTextId);
        }

        ProgressBar hpBar = (ProgressBar) findViewById(R.id.HP_progressBar);
        String imgUrl = String.format("http://www.csie.ntu.edu.tw/~r03944003/detailImg/%d.png", data.pokemonId);
        ImageLoader.getInstance().displayImage(imgUrl, detailImg);

        nameText.setText(data.name);
        levelText.setText(String.valueOf(data.level));
        currentHP.setText(data.currentHP);
        maxHP.setText(String.valueOf(data.maxHP));

        if (data.type1Index != -1) {
            type1Text.setText(OwnedPokemonInfo.typeNames[data.type1Index]);
        } else {
            type1Text.setText("");
        }

        if (data.type2Index != -1) {
            type2Text.setText(OwnedPokemonInfo.typeNames[data.type2Index]);
        } else {
            type2Text.setText("");
        }


        for (int i = 0; i < data.skills.length; i++) {
            if (data.skills[i] != null) {
                skillsText[i].setText(data.skills[i]);
            } else {
                skillsText[i].setText("");
            }
        }

        int hpPercenttage = (int) ((data.currentHP / (float) data.maxHP) * 100);
        hpBar.setProgress(hpPercenttage);


    }

}
