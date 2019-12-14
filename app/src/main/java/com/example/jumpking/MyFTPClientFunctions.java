package com.example.jumpking;

import android.content.Context;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MyFTPClientFunctions {
    FTPClient mFTPClient;
    Context context;

    public FTPClient ftpConnect(String host, String username, String password, int port, Context context) {
        this.context = context;
        FTPClient ftp = null;
        try {
            mFTPClient = new FTPClient();

            mFTPClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {

                boolean status = mFTPClient.login(username, password);

                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                FileOutputStream outputStream = null;
                boolean success = false;
                try {
                    outputStream = context.openFileOutput("ftpdata.txt",Context.MODE_PRIVATE);
                    success = mFTPClient.retrieveFile("\\My Documents\\score.txt", outputStream);
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }

                return ftp;
            }
        } catch (Exception e) {
            Log.d("errormesage", "Error: could not connect to host " + host);
        }
        return ftp;
    }

    public FTPClient Upload(String host, String username, String password, int port, Context context) {
        this.context = context;
        FTPClient ftp = null;
        try {
            mFTPClient = new FTPClient();

            mFTPClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {

                boolean status = mFTPClient.login(username, password);

                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();


                FileInputStream in = null;
                in = this.context.openFileInput("ftpdata.txt");
                boolean result = mFTPClient.storeFile("\\My Documents\\score.txt", in);
                in.close();
                if (result) Log.v("upload result", "succeeded");

                return ftp;
            }
        } catch (Exception e) {
            Log.d("errormesage", "Error: could not connect to host " + host);
        }
        return ftp;
    }
}
