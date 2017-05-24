package cn.wjh1119.webprogramming;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.URL;

import static cn.wjh1119.webprogramming.NetworkUtil.getMovieJsonStrByHttpURLConnection;
import static cn.wjh1119.webprogramming.NetworkUtil.getMovieJsonStrByOkHttp;
import static cn.wjh1119.webprogramming.UrlUtil.getRequestUrl;


public class GetActivity extends AppCompatActivity {

    private TextView textView;
    private HttpHandler httpHandler;
    public final static int MSG_SHOW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        httpHandler = new HttpHandler(this);

        Button retrofitButton = (Button) findViewById(R.id.btn_get_retrofit);
        retrofitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "retrofit";
                ConnectThread connectThread = new ConnectThread(mode);
                connectThread.start();
            }


        });

        Button okHttpButton = (Button) findViewById(R.id.btn_get_okhttp);
        okHttpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "OkHttp";
                ConnectThread connectThread = new ConnectThread(mode);
                connectThread.start();
            }


        });

        Button httpURLConnectionButton = (Button) findViewById(R.id.btn_get_httpurlconnection);
        httpURLConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "httpURLConnection";
                ConnectThread connectThread = new ConnectThread(mode);
                connectThread.start();
            }


        });
        textView = (TextView) findViewById(R.id.textView_get);
    }

    private class ConnectThread extends Thread {

        private String mode;

        public ConnectThread(String mode) {
            this.mode = mode;
        }

        @Override
        public void run() {
            URL requestUrl;
            switch (mode){
                case "retrofit":
                    NetworkUtil.getMovieJsonStrByRetrofit(httpHandler);
                    httpHandler.obtainMessage(MSG_SHOW,"use " + mode + " \n").sendToTarget();
                    break;
                case "httpURLConnection":
                    httpHandler.obtainMessage(MSG_SHOW,"use " + mode + " \n").sendToTarget();
                    requestUrl = getRequestUrl(httpHandler, "popular");
                    //使用HttpURLConnection
                    getMovieJsonStrByHttpURLConnection(httpHandler, requestUrl);
                    break;
                case "OkHttp":
                    requestUrl = getRequestUrl(httpHandler, "popular");
                    //使用Okhttp
                    getMovieJsonStrByOkHttp(httpHandler, requestUrl);
                    httpHandler.obtainMessage(MSG_SHOW,"use " + mode + " \n").sendToTarget();
                    break;
                default:
                    httpHandler.obtainMessage(MSG_SHOW,"mode is wrong \n").sendToTarget();
                    break;
            }
        }
    }

    public static class HttpHandler extends Handler {

        WeakReference<GetActivity> httpURLConnectionActivityWR;

        public HttpHandler(GetActivity activity) {
            httpURLConnectionActivityWR = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final GetActivity getActivity = httpURLConnectionActivityWR.get();
            switch (msg.what){
                case MSG_SHOW:
                    getActivity.textView.append(msg.obj.toString());
                    break;
            }

        }
    }
}
