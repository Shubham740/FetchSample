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

/**
 * this class is helpful to  download the File from APi
 */
public class FileDownloadAsncTask extends AsyncTask<String, Boolean, Boolean> implements FetchListener {
    private String filePath;
    private String fileName;
    private String url;
    private Context context;
    private Fetch fetch;
    private IFileDownloadListener iFileDownloadListener;
    String TAG = FileDownloadAsncTask.class.getSimpleName();

    /**
     * this method is used to initialize  the data
     *
     * @param iFileDownloadListener :  it contains the Object of IFileDownloadAsyncTask
     * @param context               : it contains the context
     * @param fileName              : it contains the filename
     * @param filePath              : it contains the file path
     * @param url                   : it contains the URL
     */
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

    /**
     * this method is used to initialize  the Fetch Configuration
     * and add the  request in fetch
     */
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

    /**
     * this method is called when the item is added in the queue
     *
     * @param download : it contains  the reference of Download Class
     * @param b
     */
    @Override
    public void onQueued(Download download, boolean b) {
        Log.d(TAG, "onQueued ");
    }

    /**
     * this method  is called when the item is completely downloaded
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onCompleted(Download download) {
        Log.d(TAG, "onCompleted ");
        iFileDownloadListener.onFileDownload(true);

    }
    /**
     * this method  is called when the item get some error in downloading class
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onError(Download download) {
        Log.d(TAG, "onError ");
    }
    /**
     * this method  is called when the item  is downloading and it will give  us the progress
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onProgress(Download download, long l, long l1) {
        Log.d(TAG, "onProgress ");
    }
    /**
     * this method  is called when the  downloading item is paused
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onPaused(Download download) {
        Log.d(TAG, "onPaused ");
    }
    /**
     * this method  is called when the item is resumed for downloading
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onResumed(Download download) {
        Log.d(TAG, "onResumed ");
    }
    /**
     * this method  is called when the item is cancelled to download
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onCancelled(Download download) {
        Log.d(TAG, "onCancelled ");
    }
    /**
     * this method  is called when the item is removed from the queue to downlaod
     *
     * @param download : it contains the object of download class
     */
    @Override
    public void onRemoved(Download download) {
        Log.d(TAG, "onRemoved ");
    }
    /** Called when a download is deleted and is no longer managed by Fetch or contained in
     * the fetch database. The downloaded file is deleted. The status of a download will be
     * Status.DELETED.
     * @param download An immutable object which contains a current snapshot of all the information
     * about a specific download managed by Fetch.
     * */
    @Override
    public void onDeleted(Download download) {
        Log.d(TAG, "onDeleted ");
    }

    /**
     * this interface is used to provide the callback
     */
    interface IFileDownloadListener {
        public void onFileDownload(boolean status);

    }

}
