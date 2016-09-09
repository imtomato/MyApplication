package com.example.user.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/9/5.
 */
public class OwnedPokemonInfo implements Parcelable{

    public static final int maxNumSkills = 4;
    public static String[] typeNames;


    public String name;
    public int pokemonId;
    public int level;
    public int currentHP;
    public int maxHP;
    public int type1Index;
    public int type2Index;

    public String[] skills;

    public boolean isSelected;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.pokemonId);
        dest.writeInt(this.level);
        dest.writeInt(this.currentHP);
        dest.writeInt(this.maxHP);
        dest.writeInt(this.type1Index);
        dest.writeInt(this.type2Index);
        dest.writeStringArray(this.skills);
    }

    public OwnedPokemonInfo() {
    }

    protected OwnedPokemonInfo(Parcel in) {
        this.name = in.readString();
        this.pokemonId = in.readInt();
        this.level = in.readInt();
        this.currentHP = in.readInt();
        this.maxHP = in.readInt();
        this.type1Index = in.readInt();
        this.type2Index = in.readInt();
        this.skills = in.createStringArray();
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
}
