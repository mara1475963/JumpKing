package com.example.jumpking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;

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
    TextView type;
    FTPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        new FtpTask().execute(true);

        Intent intent = getIntent();
        boolean add = intent.getBooleanExtra("add", false);

        load = findViewById(R.id.load);
        save = findViewById(R.id.send);
        nick = findViewById(R.id.nick);
        type = findViewById(R.id.type);

        if(add){
            jumps = intent.getIntExtra("jumps", 0);
            falls = intent.getIntExtra("falls", 0);

            save.setVisibility(View.VISIBLE);
            nick.setVisibility(View.VISIBLE);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String data = LoadScore(v);
                    String data2 = LoadScoreGlobal(v,nick.getText().toString());
                    SaveScore(v, data);
                    SaveScoreGlobal(v,data2);
                  //  Show(data);
                    Show(data2);
                    type.setText("Global Score");
                    new FtpTask().execute(false);

                    nick.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nick.getWindowToken(), 0);
                }
            });
        }
        else{
            type.setText("Local Score");
            String data = LoadGlobal();
            Show(data);

            new FtpTask().execute(false);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.score_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.local) {
            type.setText("Local Score");
            String data = Load();
            Show(data);
        }
        if (id == R.id.global)
        {
            type.setText("Global Score");
            String data = LoadGlobal();
            Show(data);
        }

        return true;
    }

    public void Show(String data){

        load.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);

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

    private void SaveScoreGlobal(View v, String data) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput("ftpdata.txt", MODE_PRIVATE);
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

    public String LoadScore(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput("scr.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int i = 1;
            boolean inserted = false;
            String nick = String.valueOf(jumps) + " jumps";

                while ((text = br.readLine()) != null) {
                    if(text == ""){
                        break;
                    }
                    if(!inserted) {
                    StringTokenizer st = new StringTokenizer(text, " ");
                    while (st.hasMoreTokens()) {
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

    public String LoadScoreGlobal(View v, String nick) {
        FileInputStream fis = null;
        try {
            fis = openFileInput("ftpdata.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int i = 1;
            boolean inserted = false;
            nick += ": " + String.valueOf(jumps) + " jumps";

            while ((text = br.readLine()) != null) {
                if(text.length() < 2){
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
    public String LoadGlobal() {
        FileInputStream fis = null;

        try {
            fis = openFileInput("ftpdata.txt");
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
    private class FtpTask extends AsyncTask<Boolean, Void, FTPClient> {

        protected FTPClient doInBackground(Boolean... args) {

        if(args[0]) {
            MyFTPClientFunctions ftpconnect = new MyFTPClientFunctions();
            FTPClient ftp = ftpconnect.ftpConnect("ftp.drivehq.com", "mara22", "myftppassword", 21, getApplicationContext());
            return ftp;
        }
        else {
            MyFTPClientFunctions ftpconnect = new MyFTPClientFunctions();
            FTPClient ftp2 = ftpconnect.Upload("ftp.drivehq.com", "mara22", "myftppassword", 21, getApplicationContext());
            return ftp2;
        }

        }

        protected void onPostExecute(FTPClient result) {
            Log.v("FTPTask","download/upload complete");
            client = result;
            //Where ftpClient is a instance variable in the main activity
        }
    }
}





