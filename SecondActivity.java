package com.example.myapplication;

import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;

public class SecondActivity extends Activity {
    private int correct;
    private int optimal;
    private int times= 1 ;
    private int pass;
    private int fail;
    private int testnumber;

    int binarySearch(int arr[], int l, int r, int x){
        if (r >= l) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == x) {
                return mid;
            }
            else if (arr[mid] > x){
                optimal++;
                return binarySearch(arr, l, mid - 1, x);
            }
            else {
                optimal++;
                return binarySearch(arr, mid + 1, r, x);
            }
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent= getIntent();
        Bundle retrieve = intent.getExtras();
        correct= Integer.parseInt(retrieve.getString("keyName1"));
        pass= Integer.parseInt(retrieve.getString("keySuccess"));
        testnumber= Integer.parseInt(retrieve.getString("keyName2"));
        int[] arr;
        arr= new int[100];
        for(int i=0;i<100;i++){
            arr[i]=i+1;
        }
        optimal=1;
        binarySearch(arr,0,99,correct);

    }

    public void result(){
        TextView result = findViewById(R.id.heading);
        result.setText(String.format("\t\t\t\tResult\nTest Number : %d", testnumber ));
        TextView failure = findViewById(R.id.textfail);
        failure.setText(String.format("\t\tFailures : \n%d", fail));
        TextView success = findViewById(R.id.textsuccess);
        success.setText(String.format("\t\tSuccesses : \n%d", pass));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(SecondActivity.this, "Please wait while we prepare for the next round" , Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 3000);
    }

    public String generateString(int a,int b){
        String hex= null;
        if(Math.abs(a-b)>25){
            hex = "#" + Integer.toHexString((100-Math.abs(a-b))*255/100)+"FF0000";
        }
        else{
            hex = "#"+ Integer.toHexString((100-Math.abs(a-b))*255/100)+"00FF00";
        }
        return hex;
    }

    public void resultDeclare(View ButtonView) {
        EditText guesstext = findViewById(R.id.editText2);
        if (guesstext.getText().toString().equals("") || Integer.parseInt(guesstext.getText().toString())>100 || Integer.parseInt(guesstext.getText().toString())==0)
            Toast.makeText(SecondActivity.this, "Please add age from 1-100", Toast.LENGTH_SHORT).show();
        else {
            int guess;
            Intent intent = new Intent();
            TextView result = findViewById(R.id.Result);
            ConstraintLayout constraintLayout;
            constraintLayout = findViewById(R.id.constraintLayout1);
            int color;
            guess = Integer.parseInt(guesstext.getText().toString());
            if (times <= optimal) {
                if (guess > correct) {
                    result.setText("Guess is higher than correct answer");
                } else if (guess < correct) {
                    result.setText("Guess is lower than correct answer ");
                } else {
                    result.setText("Guess is correct,You've won the game");
                    pass++;
                    fail = testnumber - pass;
                    intent.putExtra("updateSuccess", pass + "");
                    setResult(RESULT_OK, intent);
                    result();
                }
                color = Color.parseColor(generateString(guess, correct) + "");
                constraintLayout.setBackgroundColor(color);
            } else {
                result.setText("You've lost the game");
                fail = testnumber - pass;
                result();
            }
            times++;
        }
    }
}
