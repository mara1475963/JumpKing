package com.example.jumpking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    Button newGame;
    Button continueGame;
    Button score;
    SharedPreferences preset;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        preset = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preset.edit();

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.soundtrack);
        mp.start();
        //setContentView(new GameView(this));
        setContentView(R.layout.activity_main);


        newGame = findViewById(R.id.newgame);
        continueGame = findViewById(R.id.continueGame);
        score = findViewById(R.id.score);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("com.example.jumpking.newGame",true);
                editor.commit();
                mp.stop();

                Intent intent =new Intent(getApplicationContext(),GameActivity.class);
                startActivity(intent);
            }
        });
        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("com.example.jumpking.newGame",false);
                editor.commit();
                mp.stop();

                Intent intent =new Intent(getApplicationContext(),GameActivity.class);
                startActivity(intent);
            }
        });
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intent);
            }
        });


    }
}
