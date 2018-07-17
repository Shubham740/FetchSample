package com.example.user.fetchsample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.Func;

import static com.example.user.fetchsample.AppUtils.getFilePath;

public class FileDownloadAsncTask extends AsyncTask<String, Boolean, Boolean> implements FetchListener {
    private String filePath;
    private String fileName;
    private String url;
    private Context context;
    private Fetch fetch;
    private IFileDownloadListener iFileDownloadListener;
    String TAG = FileDownloadAsncTask.class.getSimpleName();

    public FileDownloadAsncTask(IFileDownloadListener iFileDownloadListener, Context context, String fileName, String filePath, String url) {
        this.context = context;
        this.fileName = fileName;
        this.url = url;
        this.filePath = filePath;
        this.iFileDownloadListener = iFileDownloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void initFetch() {
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(3)
                .build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);
        final Request request = new Request(url, filePath + fileName);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(Request request) {
                request.getFile();
            }
        }, new Func<Error>() {
            @Override
            public void call(Error error) {
                Log.e(TAG, error.toString());
            }
        });
        fetch.addListener(this);
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        initFetch();
        return true;
    }

    @Override
    public void onQueued(Download download, boolean b) {

    }

    @Override
    public void onCompleted(Download download) {
        iFileDownloadListener.onFileDownload(true);

    }

    @Override
    public void onError(Download download) {

    }

    @Override
    public void onProgress(Download download, long l, long l1) {

    }

    @Override
    public void onPaused(Download download) {

    }

    @Override
    public void onResumed(Download download) {

    }

    @Override
    public void onCancelled(Download download) {

    }

    @Override
    public void onRemoved(Download download) {

    }

    @Override
    public void onDeleted(Download download) {

    }

    interface IFileDownloadListener {
        public void onFileDownload(boolean status);

    }

}
