package com.example.user.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextView.OnEditorActionListener {


    TextView infoText;
    TextView nameText;
    RadioGroup optionsGrp;
    Button confirmBtn;
    String nameOfTheTrainer;
    int selectedOptionIndex;

    static final String[] pokemonNames = {"小火龍", "傑尼龜", "妙蛙種子"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        infoText = (TextView) findViewById(R.id.infoText);
        nameText = (TextView) findViewById(R.id.nameText);
        optionsGrp = (RadioGroup) findViewById(R.id.optionsGroup);
        confirmBtn = (Button) findViewById(R.id.confirmButton);

        nameText.setOnEditorActionListener(this);
        nameText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        confirmBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        //判斷哪個UI傳來的，是否為confirmBtn
        //在一個view有多個button時須作判斷
        int viewId = v.getId();

        if (viewId == R.id.confirmButton) {
            nameOfTheTrainer = nameText.getText().toString();

            int selectedRadioButtonViewId = optionsGrp.getCheckedRadioButtonId(); //找出被選的radioButtonID
            View selectedRadioButton = optionsGrp.findViewById(selectedRadioButtonViewId); //透過ID找到radioButtonView
            selectedOptionIndex = optionsGrp.indexOfChild(selectedRadioButton); //找出被選的radioButton在第幾個位置

            String welcomeMessage = String.format
                    ("你好，訓練家%s !歡迎來到神奇寶貝的世界，你的第一個夥伴是%s "
                            , nameOfTheTrainer
                            , pokemonNames[selectedOptionIndex]);

            infoText.setText(welcomeMessage);


        }


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE){

            //dissmiss virtual keyboard
            InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(),0);

            //simulate button clicked
            //按下虛擬鍵盤確認等於使用者按按鈕
            confirmBtn.performClick();





        }

        return false;
    }
}
