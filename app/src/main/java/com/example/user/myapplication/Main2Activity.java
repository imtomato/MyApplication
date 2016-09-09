package com.example.user.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {

    TextView infoText;
    TextView nameText;
    RadioGroup optionGroup;
    Button confirmBtn;
    String nameOfTrainer;
    CheckBox infoCheck;
    int selectedPokemonIndex;
    static final String[] pokemonNames = {"小火龍", "傑尼龜", "妙蛙種子"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        infoText = (TextView) findViewById(R.id.infoText);
        nameText = (TextView) findViewById(R.id.nameText);
        optionGroup = (RadioGroup) findViewById(R.id.optionGroup);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        infoCheck = (CheckBox) findViewById(R.id.infocheck);

        nameText.setOnEditorActionListener(this);
        nameText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        infoCheck.setOnCheckedChangeListener(this);
        confirmBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        String welcomeMessage = "";
        if (viewId == R.id.confirmBtn) {

            int selectedGroupBtnViewId = optionGroup.getCheckedRadioButtonId();
            View selectedGroupBtnView = optionGroup.findViewById(selectedGroupBtnViewId);
            selectedPokemonIndex = optionGroup.indexOfChild(selectedGroupBtnView);

            if (selectedPokemonIndex >= 0 && selectedPokemonIndex < 3) {
                welcomeMessage = String.format
                        ("你好，訓練家%s    歡迎來到神奇寶貝的世界，你的第一個夥伴是%s "
                                , nameOfTrainer
                                , pokemonNames[selectedPokemonIndex]);
            } else {
                welcomeMessage = String.format("你好，訓練家%s，請選擇神奇寶貝",nameOfTrainer);
            }
            infoText.setText(welcomeMessage);

        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //dissmiss virtual keyboard
            InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //simulate button clicked
            //按下虛擬鍵盤確認等於使用者按按鈕
            confirmBtn.performClick();
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        if (viewId == R.id.infocheck) {
            if (!isChecked) {
                nameOfTrainer = nameText.getText().toString();
            } else {
                nameOfTrainer = "";
            }
        }
    }
}
