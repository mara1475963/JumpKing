package com.example.jumpking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ScoreActivity extends AppCompatActivity {

    Button save;
    EditText nick;
    int falls, jumps;
    TextView load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        boolean add = intent.getBooleanExtra("add", false);

        load = findViewById(R.id.load);
        save = findViewById(R.id.send);
        nick = findViewById(R.id.nick);

        if(add){
            jumps = intent.getIntExtra("jumps", 0);
            falls = intent.getIntExtra("falls", 0);

            save.setVisibility(View.VISIBLE);
            nick.setVisibility(View.VISIBLE);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String data = LoadScore(v,nick.getText().toString());
                    SaveScore(v, data);
                    Show(data);

                    nick.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nick.getWindowToken(), 0);
                }
            });


        }
        else{
            String data = Load();
            Show(data);

        }
    }
    public void Show(String data){

        load.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);
        nick.setVisibility(View.GONE);

        load.setText(data);
    }



    private void SaveScore(View v, String data) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput("scr.txt", MODE_PRIVATE);
            fos.write(data.getBytes());

            this.nick.getText().clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String LoadScore(View v, String nick) {
        FileInputStream fis = null;

        try {
            fis = openFileInput("scr.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int i = 1;
            boolean inserted = false;
            nick += ": " + String.valueOf(jumps) + " jumps";

                while ((text = br.readLine()) != null) {
                    if(text == ""){
                        break;
                    }
                    if(!inserted) {
                    StringTokenizer st = new StringTokenizer(text, " ");
                    while (st.hasMoreTokens()) {
                        st.nextToken();
                        st.nextToken();
                        int jumps = Integer.parseInt(st.nextToken());
                        if (jumps < this.jumps) {
                            st.nextToken();
                        } else {
                            inserted = true;
                            sb.append(String.valueOf(i) + ". " + nick).append("\n");
                            i++;
                            st.nextToken();
                        }
                    }
                }
                    if(text.charAt(3) == ' '){
                        sb.append(String.valueOf(i) + ". " + (text.substring(4))).append("\n");
                    }
                    else {
                        sb.append(String.valueOf(i) + ". " + (text.substring(3))).append("\n");
                    }

                i++;
            }
            if(!inserted){
                sb.append(String.valueOf(i) + ". " + nick).append("\n");
            }
            if(i==1){
                return "1. " + nick;
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
    public String Load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput("scr.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int i = 1;
            while ((text = br.readLine()) != null) {
                if(text == ""){
                    break;
                }

                if(text.charAt(3) == ' '){
                    sb.append(String.valueOf(i) + ". " + (text.substring(4))).append("\n");
                }
                else {
                    sb.append(String.valueOf(i) + ". " + (text.substring(3))).append("\n");
                }

                i++;
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}


