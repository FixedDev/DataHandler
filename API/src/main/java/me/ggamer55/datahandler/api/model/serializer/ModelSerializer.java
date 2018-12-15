

package me.ggamer55.datahandler.api.model.serializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import me.ggamer55.datahandler.api.model.Model;

public interface ModelSerializer<Complete extends Model> {
    @SuppressWarnings("unchecked")
    JsonObject serialize(Complete model);

    @SuppressWarnings("unchecked")
    Complete deserialize(JsonObject object, TypeToken<Complete> type);
}
