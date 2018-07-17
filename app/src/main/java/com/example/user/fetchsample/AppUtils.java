package com.example.user.fetchsample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *@author :Shubham Gupta
 */
public class AppUtils {
    private static int READ_PERMISSION_REQUEST_CODE = 2;
    private static int WRITE_PERMISSION_REQUEST_CODE = 3;
    public static int TIME_DELAY = 12000;
    public static String FILE_NAME = "Prospectus.pdf";
    public static String FILE_URL = "http://jam.iitb.ac.in/brochure.pdf";

    /**
     * this method is used to provice the read storage permission
     * @param activity
     */
    public static void requestReadStorage(Activity activity) {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(activity, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(activity, permission_list, AppUtils.READ_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * this method is used to provide the Write Storage permission
     * @param activity : it contains the reference of Activity
     */
    public static void reqWriteStorage(Activity activity) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(activity, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(activity, permission_list, AppUtils.WRITE_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * this method is used to provide the file path
     * @return
     */
    public static String getFilePath() {
        return Environment.getExternalStorageDirectory().toString() + "/";
    }

    /**
     * @param pContext
     * @return
     * @note android.permission.ACCESS_NETWORK_STATE is required
     */
    private static NetworkInfo getActiveNetwork(Context pContext) {
        ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMngr == null ? null : conMngr.getActiveNetworkInfo();
    }

    public static boolean isNetworkEnabled(Context pContext) {
        NetworkInfo activeNetwork = getActiveNetwork(pContext);
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * this function is used to show our file  in different App
     *
     * @param context
     * @param fileName
     */
    public static void readFromFile(Context context, String fileName) {
        File file = new File(Environment
                .getExternalStorageDirectory().toString()
                + "/" + fileName);
        Uri uri = Uri.parse("file://" + file.getAbsolutePath());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
    }
  

}
