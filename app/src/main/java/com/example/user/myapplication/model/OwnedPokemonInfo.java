package com.example.user.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/9/5.
 */
public class OwnedPokemonInfo implements Parcelable{

    public final static String nameKey = "name";
    public final static String pokeIdKey = "pokeId";
    public final static String levelKey = "level";
    public final static String currentHPKey = "currentHP";
    public final static String maxHPKey = "maxHP";
    public final static String type1Key = "type1";
    public final static String type2Key = "type2";
    public final static String skillKey = "skill";


    public static final int maxNumSkills = 4;
    public static String[] typeNames;


    private String name;
    private int pokemonId;
    private int level;
    private int currentHP;
    private int maxHP;
    private int type1Index;
    private int type2Index;

    private String[] skills;

    public boolean isSelected;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeInt(this.getPokemonId());
        dest.writeInt(this.getLevel());
        dest.writeInt(this.getCurrentHP());
        dest.writeInt(this.getMaxHP());
        dest.writeInt(this.getType1Index());
        dest.writeInt(this.getType2Index());
        dest.writeStringArray(this.getSkills());
    }

    public OwnedPokemonInfo() {
    }

    protected OwnedPokemonInfo(Parcel in) {
        this.setName(in.readString());
        this.setPokemonId(in.readInt());
        this.setLevel(in.readInt());
        this.setCurrentHP(in.readInt());
        this.setMaxHP(in.readInt());
        this.setType1Index(in.readInt());
        this.setType2Index(in.readInt());
        this.setSkills(in.createStringArray());
    }

    public static final Creator<OwnedPokemonInfo> CREATOR = new Creator<OwnedPokemonInfo>() {
        @Override
        public OwnedPokemonInfo createFromParcel(Parcel source) {
            return new OwnedPokemonInfo(source);
        }

        @Override
        public OwnedPokemonInfo[] newArray(int size) {
            return new OwnedPokemonInfo[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getType1Index() {
        return type1Index;
    }

    public void setType1Index(int type1Index) {
        this.type1Index = type1Index;
    }

    public int getType2Index() {
        return type2Index;
    }

    public void setType2Index(int type2Index) {
        this.type2Index = type2Index;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
