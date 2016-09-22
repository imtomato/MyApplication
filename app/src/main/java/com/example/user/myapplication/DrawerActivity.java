package com.example.user.myapplication;

import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.myapplication.utility.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class DrawerActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{

    AccountHeader headerResult;
    IProfile profile;
    Drawer naviDrawer;
    final static int defaultSelectedIndex = 0;
    Fragment[] fragments;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        fragments = new Fragment[3];
        fragments[0] = PokemonListFragment.newInstance();
        fragments[1] = TestFragment.newInstance("fake 1");
        fragments[2] = TestFragment.newInstance("fake 2");

        fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        String profileName = "batman";
        String profileEmail = "batman@gmail.com";
        Drawable profileIcon = Utils.getDrawable(this,R.drawable.profile3);
        profile = new ProfileDrawerItem().withEmail(profileEmail).withName(profileName).withIcon(profileIcon);

        buildDrawerHeader(true,savedInstanceState);
        naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .inflateMenu(R.menu.drawer_view)
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .build();

        //設定預設選第一個Item,並觸發ClickListener
        naviDrawer.setSelectionAtPosition(defaultSelectedIndex+1,true);




    }


    public void displayFragment(Fragment fragment) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        //追蹤transaction
        transaction.addToBackStack(null);
        transaction.commit();

    }


    void buildDrawerHeader(boolean compact,Bundle savedInstatnce){

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(Utils.getDrawable(this,R.drawable.header))
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstatnce)
                .build();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        headerResult.saveInstanceState(outState);
        naviDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        displayFragment(fragments[position - 1]);
        return false; //Navigation Bar 點擊Item後會縮回
    }
}
