package com.example.user.fetchsample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.Func;

import java.util.Map;

import static com.example.user.fetchsample.AppUtils.getFilePath;

public class MainActivity extends AppCompatActivity implements FileDownloadAsncTask.IFileDownloadListener {
    private Fetch fetch;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppUtils.requestReadStorage(this);
        AppUtils.reqWriteStorage(this);
        initLayout();
        FileDownloadAsncTask fileDownloadAsncTask = new FileDownloadAsncTask(this,this, AppUtils.FILE_NAME, AppUtils.getFilePath(), AppUtils.FILE_URL);
        fileDownloadAsncTask.execute();
    }

    private void initLayout() {
        progressBar = findViewById(R.id.progressBarId);
    }

    @Override
    public void onFileDownload(boolean status) {
        if (status) {
            progressBar.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppUtils.readFromFile(MainActivity.this, AppUtils.FILE_NAME);
                }
            }, AppUtils.TIME_DELAY);

        }
    }
}
