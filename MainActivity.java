package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int age;
    private int testnumber;
    private int success=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setAge(View buttonView){
        Random rand= new Random();
        age = rand.nextInt(100)+1;
        testnumber++;
        Toast.makeText(MainActivity.this, "Random age generated" , Toast.LENGTH_SHORT).show();
        Log.i("Tag",age+"");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                success = Integer.parseInt(data.getStringExtra("updateSuccess"));
            }
        }
    }

    public void Proceed(View view) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("keyName1", age + "");
            intent.putExtra("keyName2", testnumber + "");
            intent.putExtra("keySuccess", success + "");
            startActivityForResult(intent, 1);
    }

    public void Exit(View view1){
        finish();
    }
}
