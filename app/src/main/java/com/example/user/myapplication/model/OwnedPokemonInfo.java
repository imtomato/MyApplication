package com.example.user.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/9/5.
 */


@ParseClassName("OwnedPokemonInfo")
public class OwnedPokemonInfo extends ParseObject implements Parcelable{

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
        return getString(nameKey);
    }

    public void setName(String name) {
        put(nameKey,name);
    }

    public int getPokemonId() {
        return getInt(pokeIdKey);
    }

    public void setPokemonId(int pokemonId) {
        put(pokeIdKey,pokemonId);
    }

    public int getLevel() {
        return getInt(levelKey);
    }

    public void setLevel(int level) {
        put(levelKey,level);
    }

    public int getCurrentHP() {
        return getInt(currentHPKey);
    }

    public void setCurrentHP(int currentHP) {
        put(currentHPKey,currentHP);
    }

    public int getMaxHP() {
        return getInt(maxHPKey);
    }

    public void setMaxHP(int maxHP) {
        put(maxHPKey,maxHP);
    }

    public int getType1Index() {
        return getInt(type1Key);
    }

    public void setType1Index(int type1Index) {
        put(type1Key,type1Index);
    }

    public int getType2Index() {
        return getInt(type2Key);
    }

    public void setType2Index(int type2Index) {
        put(type2Key,type2Index);
    }

    boolean skillHaveBeenInited = false;
    boolean skillHaveBeenModified = false;


    public String[] getSkills() {
        if(!skillHaveBeenInited){
            skillHaveBeenInited = true;
            this.skills = readSkillFromParseObjectStorage();
        }else if(skillHaveBeenModified){
            skillHaveBeenModified = false;
            this.skills = readSkillFromParseObjectStorage();
        }
        return skills;
    }

    String[] readSkillFromParseObjectStorage() {
        ArrayList<String> skillList = (ArrayList) getList(skillKey);
        String[] skillArray = new String[maxNumSkills];
        for(int i = 0;i<skillList.size();i++){
            skillArray[i] = skillList.get(i);
        }
        return skillArray;
    }

    public void setSkills(String[] skills) {
        ArrayList<String> skillList = new ArrayList<>();
        for(String skill:skills){
            if(skills != null)
                skillList.add(skill);
        }
        put(skillKey,skillList);
        skillHaveBeenModified  = true;
    }

    public static ParseQuery<OwnedPokemonInfo> getQuery(){
        return ParseQuery.getQuery(OwnedPokemonInfo.class);
    }

    public static final String localDBTableName = OwnedPokemonInfo.class.getSimpleName();

    public static void initTable(final ArrayList<OwnedPokemonInfo> ownedPokemonInfos){
        OwnedPokemonInfo.unpinAllInBackground(localDBTableName);

        //清理local及remote table record
        OwnedPokemonInfo.getQuery().findInBackground(new FindCallback<OwnedPokemonInfo>() {
            @Override
            public void done(List<OwnedPokemonInfo> objects, ParseException e) {
                if(e == null && objects != null){
                    OwnedPokemonInfo.deleteAllInBackground(objects);
                }

                saveToDB(ownedPokemonInfos);
            }

        });
    }

    public static void saveToDB(ArrayList<OwnedPokemonInfo> ownedPokemonInfos){
        OwnedPokemonInfo.pinAllInBackground(ownedPokemonInfos);
        for(OwnedPokemonInfo ownedPokemonInfo : ownedPokemonInfos){
            ownedPokemonInfo.saveEventually(); //若沒網路連線 會先catch，帶恢復連線後，再存入
        }
    }




}
