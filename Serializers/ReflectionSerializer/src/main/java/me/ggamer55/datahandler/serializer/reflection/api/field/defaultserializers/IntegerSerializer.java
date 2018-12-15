

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class IntegerSerializer implements FieldSerializer<Integer> {
    @Override
    public JsonElement serialize(Integer object) {
        return new JsonPrimitive(object);
    }

    @Override
    public Integer deserialize(JsonElement element) {
        return element.getAsInt();
    }
}
