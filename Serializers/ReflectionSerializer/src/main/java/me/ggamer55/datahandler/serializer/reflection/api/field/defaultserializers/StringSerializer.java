

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class StringSerializer implements FieldSerializer<String> {
    @Override
    public JsonElement serialize(String object) {
        return new JsonPrimitive(object);
    }

    @Override
    public String deserialize(JsonElement element) {
        return element.getAsString();
    }
}
