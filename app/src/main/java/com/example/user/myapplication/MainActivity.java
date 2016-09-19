package com.example.user.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;




public class MainActivity extends CustomizedActivity implements View.OnClickListener,TextView.OnEditorActionListener{


    TextView infoText;
    TextView nameText;
    RadioGroup optionsGrp;
    Button confirmBtn;
    String nameOfTheTrainer;
    int selectedOptionIndex;
    Handler uiHandler;

    public static final String selectedOptionIndexKey = "selectedOptionIndex";

    public static final String nameOfTheTrainerKey = "nameOfTheTrainer";
    public static final String selectedIndexKey = "selectedIndex";

    static final String[] pokemonNames = {"小火龍", "傑尼龜", "妙蛙種子"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityName = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        infoText = (TextView) findViewById(R.id.infoText);
        nameText = (TextView) findViewById(R.id.nameText);
        optionsGrp = (RadioGroup) findViewById(R.id.optionsGroup);
        confirmBtn = (Button) findViewById(R.id.confirmButton);

        nameText.setOnEditorActionListener(this);
        nameText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        confirmBtn.setOnClickListener(this);

        uiHandler = new Handler(getMainLooper());

        //從sharedPrefrence取得資訊
        SharedPreferences preferences = getSharedPreferences(Application.class.getSimpleName(),MODE_PRIVATE);
        nameOfTheTrainer = preferences.getString(nameOfTheTrainerKey,null);
        selectedOptionIndex = preferences.getInt(selectedIndexKey,0);
        //判斷是否為第一次開啟APP
        if(nameOfTheTrainer == null){
            //TODO:show confirm button, nameEditText and optionGroup

        }else{
            //TODO: show progressBar

        }









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

            //設定延遲切換畫面(Activity)
            uiHandler.postDelayed(jumpToNewActivityTask,3*1000);



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

    Runnable jumpToNewActivityTask = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.putExtra(selectedOptionIndexKey,selectedOptionIndex);
            intent.setClass(MainActivity.this, PokemonListActivity.class);
            startActivity(intent);

            //Mainactivity 會進入destroy 階段
            MainActivity.this.finish();

        }
    };


}
