package me.ggamer55.datahandler.serializers.gson;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.model.serializer.ModelSerializer;

public class GsonModelSerializer<Complete extends Model> implements ModelSerializer<Complete> {

    private Gson gson;

    public GsonModelSerializer(GsonBuilder gson) {
        GsonBuilder gsonBuilder = Preconditions.checkNotNull(gson).enableComplexMapKeySerialization();

        this.gson = gsonBuilder.create();
    }

    @Override
    public JsonObject serialize(Complete model) {
        return gson.toJsonTree(model).getAsJsonObject();
    }

    @Override
    public Complete deserialize(JsonObject object, TypeToken<Complete> type) {
        return gson.fromJson(object, type.getType());
    }
}
