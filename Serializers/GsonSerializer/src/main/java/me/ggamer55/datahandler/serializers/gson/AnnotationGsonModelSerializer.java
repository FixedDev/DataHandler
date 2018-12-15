package me.ggamer55.datahandler.serializers.gson;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.ggamer55.datahandler.api.Serializable;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.model.serializer.ModelSerializer;

public class AnnotationGsonModelSerializer<Complete extends Model> implements ModelSerializer<Complete> {

    private Gson gson;

    public AnnotationGsonModelSerializer(GsonBuilder gson) {
        GsonBuilder gsonBuilder = Preconditions.checkNotNull(gson)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        if ("id".equalsIgnoreCase(f.getName())) {
                            return false;
                        }
                        Serializable annotation = f.getAnnotation(Serializable.class);

                        return annotation == null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).setFieldNamingStrategy(f -> {
                    if ("id".equalsIgnoreCase(f.getName())) {
                        return "_id";
                    }
                    Serializable annotation = f.getAnnotation(Serializable.class);

                    if (annotation.value().equalsIgnoreCase("")) {
                        return f.getName();
                    }
                    return annotation.value();
                })
                .enableComplexMapKeySerialization();

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
