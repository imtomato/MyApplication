package com.example.user.myapplication;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener {


    Fragment[] fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);


        Button f1Btn = (Button) findViewById(R.id.fragmentBtn1);
        Button f2Btn = (Button) findViewById(R.id.fragmentBtn2);

        f1Btn.setOnClickListener(this);
        f2Btn.setOnClickListener(this);

        fragment = new Fragment[2];
        fragment[0] = TestFragment.newInstance("Fragment1");
        fragment[1] = TestFragment.newInstance("Fragment2");

    }


    public void displayFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        //追蹤transaction
        transaction.addToBackStack(null);
        transaction.commit();

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fragmentBtn1){
            displayFragment(fragment[0]);
        }else if(v.getId() == R.id.fragmentBtn2){
            displayFragment(fragment[1]);
        }


    }
}
