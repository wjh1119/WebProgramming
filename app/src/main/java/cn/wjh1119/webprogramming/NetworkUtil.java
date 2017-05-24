package cn.wjh1119.webprogramming;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static cn.wjh1119.webprogramming.GetActivity.MSG_SHOW;

/**
 * 从网上获取资料
 * Created by Mr.King on 2017/5/24 0024.
 */

public class NetworkUtil {

    public static String getMovieJsonStrByHttpURLConnection(GetActivity.HttpHandler handler, URL url) {
        // If there's no zip code, there's nothing to look up.  Verify size of params.

        final String LOG_TAG = "getMovieJsonStrByHttpURLConnection";

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr;

        try {

            // Create the request to Movie, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();

            //urlConnection参数设定
            urlConnection.setDoOutput(false);//是否输出,默认是false
            urlConnection.setDoInput(true);//是否读入,默认是true

            //请求不能使用缓存
            urlConnection.setUseCaches(false);

            urlConnection.setConnectTimeout(10000); //连接超时设置，单位毫秒
//            urlConnection.setReadTimeout(10000); // 读取超时设置，单位毫秒
            urlConnection.setRequestMethod("GET");//请求方式，有GET和POST,默认是GET
            urlConnection.connect();

            handler.obtainMessage(MSG_SHOW, "url connected \n").sendToTarget();
            // Read the input stream into a StringPOST
            //输入流
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.d(LOG_TAG, "movieJsonStr is " + movieJsonStr);
            handler.obtainMessage(MSG_SHOW, "successed to download Json \n").sendToTarget();
            return movieJsonStr;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static void getMovieJsonStrByOkHttp(final GetActivity.HttpHandler handler, URL url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();

        //同步
        //Call对象的execute方法是同步方法，会阻塞当前线程，其返回Response对象
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //异步
        //要想异步执行网络请求，需要执行Call对象的enqueue方法，该方法接收一个okhttp3.Callback对象，
        // enqueue方法不会阻塞当前线程，会新开一个工作线程
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Gson gson = new Gson();
                if (null != response.cacheResponse()) {
                    String str = response.body().string();

                    //使用Gson解析Json数据
//                    MovieModel movieModel = gson.fromJson(response.body().charStream(), MovieModel.class);

                    handler.obtainMessage(MSG_SHOW, "successed to download Json \n").sendToTarget();
                    Log.i("Okhttp", "cache---" + str.toString());
                    response.body().close();
                } else {
                    String str = response.body().string();
                    handler.obtainMessage(MSG_SHOW, "successed to download Json \n").sendToTarget();
                    Log.i("Okhttp", "network---" + str);
                    response.body().close();
                }
            }
        });
    }

    public static void getMovieJsonStrByRetrofit(final GetActivity.HttpHandler handler) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PopularService service = retrofit.create(PopularService.class);
        retrofit2.Call<MovieModel> call = service.getPopular("zh", BuildConfig.OPEN_MOVIE_API_KEY);
        call.enqueue(new retrofit2.Callback<MovieModel>() {
            @Override
            public void onResponse(retrofit2.Call<MovieModel> call, retrofit2.Response<MovieModel> response) {
                Log.d(getClass().getSimpleName(), "onResponse");
                String title = response.body().getResults().get(0).getTitle();
                handler.obtainMessage(MSG_SHOW, "successed to download json \nfirst movie title is " + title + " \n").sendToTarget();
            }

            @Override
            public void onFailure(retrofit2.Call<MovieModel> call, Throwable t) {
                Log.d(getClass().getSimpleName(), "onFailure");
                handler.obtainMessage(MSG_SHOW, "failed to download json \n").sendToTarget();
                t.printStackTrace();
            }
        });
    }

    public interface PopularService {
        @GET("popular")
        retrofit2.Call<MovieModel> getPopular(@Query("language") String language,
                                              @Query("api_key") String apiKey);
    }
}
