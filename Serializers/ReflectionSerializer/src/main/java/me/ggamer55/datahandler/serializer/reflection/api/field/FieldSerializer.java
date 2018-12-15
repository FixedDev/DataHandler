

package me.ggamer55.datahandler.serializer.reflection.api.field;

import com.google.gson.JsonElement;

public interface FieldSerializer<O> {
    JsonElement serialize(O object);

    O deserialize(JsonElement element) throws Exception;
}
