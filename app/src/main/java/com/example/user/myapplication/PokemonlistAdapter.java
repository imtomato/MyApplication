package com.example.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/9/5.
 */
public class PokemonlistAdapter extends ArrayAdapter<OwnedPokemonInfo> {

    int rowViewLayoutId;
    LayoutInflater mInflater;


    public PokemonlistAdapter(Context context,int layoutId,List<OwnedPokemonInfo> objects){
        super(context, layoutId, objects);
        rowViewLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        OwnedPokemonInfo data = getItem(position);
        ViewHolder viewHolder = null;
        //判斷rowView是否初始成功
        if(rowView == null){
            rowView = mInflater.inflate(rowViewLayoutId,null);
            viewHolder = new ViewHolder(rowView);
            //放到cache
            rowView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) rowView.getTag();
        }


        viewHolder.setView(data);

        return rowView;
    }

    public static class ViewHolder{
        View mRowView;

        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;

        public static PokemonlistAdapter mAdapter;

        OwnedPokemonInfo mData;
        public ViewHolder(View rowView){
            mRowView = rowView;
            mAppearanceImg = (ImageView) rowView.findViewById(R.id.appearanceImg);
            mNameText = (TextView) rowView.findViewById(R.id.nameText);
            mLevelText = (TextView) rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView) rowView.findViewById(R.id.curretHP);
            mMaxHP = (TextView) rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar) rowView.findViewById(R.id.hpBar);

        }

        //將mRowView連結data
        public void setView(OwnedPokemonInfo data){
            mData = data;
            mNameText.setText(data.name);
            mLevelText.setText(String.valueOf(data.level));
            mCurrentHP.setText(String.valueOf(data.currentHP));
            mMaxHP.setText(String.valueOf(data.maxHP));
            int HPProgress = (int) (((float)data.currentHP / data.maxHP) *100);
            mHPBar.setProgress(HPProgress);


            String imgUrl = String.format("http://www.csie.ntu.edu.tw/~r03944003/listImg/%d.png",data.pokemonId);
            ImageLoader.getInstance().displayImage(imgUrl,mAppearanceImg);


        }



    }


}
