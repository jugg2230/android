package Utils;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.Primitives;


/**
 * Created by root on 16-6-21.
 */
public class ParseJsonUtils {

    private static final String TAG_LOG = ParseJsonUtils.class.getName();


    public static <T> T getObjectFromJson(JsonElement jsonElement, Class<T> classOfT) {
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonElement, classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }
}
