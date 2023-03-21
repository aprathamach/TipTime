package com.example.tiptime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends AppCompatActivity {
    private RadioGroup rg;
    private EditText cost_of_serv;
    private boolean isRoundUp;
    private final RadioButton rb = null;
    private double costofservice;
    private double tipPercent = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Time Tip");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for round up switch
        SwitchCompat roundupSwitch = findViewById(R.id.round_up_switch);
        roundupSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> calc_display_tip());

        //for feedback
        cost_of_serv = findViewById(R.id.cost_of_service);
        rg = findViewById(R.id.tip_options);

        //for calculate button
        Button calcBtn = findViewById(R.id.calculate_button);
        calcBtn.setOnClickListener(v -> calculateTip());
    }

    @SuppressLint("NonConstantResourceId")
    public void calculateTip(){
        isRoundUp = true;
        if(cost_of_serv.getText().toString().equals("")){
            cost_of_serv.setError("Enter cost of service");
            return;
        }

        int checkedId = rg.getCheckedRadioButtonId();
        if(checkedId == -1 ){
            rb.setError("Select service feedback");
            return;
        }

        try{
            costofservice = Double.parseDouble(cost_of_serv.getText().toString());
        }catch(Exception e){
            cost_of_serv.setError("Invalid cost of service");
            return;
        }

        switch(checkedId){
            case R.id.option_twenty_percent:
                tipPercent = 0.20;
                break;
            case R.id.option_eighteen_percent:
                tipPercent = 0.18;
                break;
            case R.id.option_fifteen_percent:
                tipPercent = 0.15;
        }

        calc_display_tip(); //method for calculating and displaying the tip on the screen.
    }

    @SuppressLint("SetTextI18n")
    public void calc_display_tip(){
        boolean round_up_status = ((SwitchCompat)findViewById(R.id.round_up_switch)).isChecked();
        TextView tip_tv = findViewById(R.id.tip_result);
        tip_tv.setText(getString(R.string.tip_amount));
        String result = String.valueOf(round_up_status);
        Log.d(MainActivity.class.getSimpleName(),result);

        double tipAmount;
        if(round_up_status){
            tipAmount = Math.round(costofservice * tipPercent);
            tip_tv.setText(tip_tv.getText() + ": $ "+(int)tipAmount);
        }else{
            tipAmount = costofservice * tipPercent;
            DecimalFormat df = new DecimalFormat("#.##");
            tip_tv.setText(tip_tv.getText() + ": $ "+df.format(tipAmount));
        }
        if(!isRoundUp){
            tip_tv.setText("Tip Amount");
        }
    }
}