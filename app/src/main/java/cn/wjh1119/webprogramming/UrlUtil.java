package cn.wjh1119.webprogramming;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import static cn.wjh1119.webprogramming.GetActivity.MSG_SHOW;

/**
 * 获取Url
 * Created by Mr.King on 2017/5/24 0024.
 */

public class UrlUtil {
    private static String LOG_TAG = "UrlUtil";

    public static URL getRequestUrl(GetActivity.HttpHandler handler, String mode) {

        String BASE_URL;

        if (mode.equals("popular")) {
            BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
        } else if (mode.equals("toprated")) {
            BASE_URL = "http://api.themodb.org/3/movie/top_rated?";
        } else {
            Log.v(LOG_TAG, "mode is wrong");
            return null;
        }

        final String LANGUAGE_PARAM = "language";
        final String APPID_PARAM = "api_key";

        Uri builtPopularUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, "zh")
                .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtPopularUri.toString());
            Log.d(LOG_TAG, "url is " + url);
            handler.obtainMessage(MSG_SHOW, "create url \n").sendToTarget();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
