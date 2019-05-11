package demos.expmind.andromeda.data.local;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;
import demos.expmind.andromeda.data.Caption;

public class CaptionsConverter {

    private Gson gsonConverter = new Gson();

    @TypeConverter
    public String serializeToJSON(List<Caption> captions) {
        return gsonConverter.toJson(captions);
    }

    @TypeConverter
    public List<Caption> deserializeFromJSON(String captions) {
        Type collectionType = new TypeToken<List<Caption>>(){}.getType();
        try {
            return gsonConverter.fromJson(captions, collectionType);
        } catch (JsonParseException jpe) {
            Log.e("CaptionsConverter", "Not able to deserialize list of captions");
            return new ArrayList<>();
        }
    }
}
