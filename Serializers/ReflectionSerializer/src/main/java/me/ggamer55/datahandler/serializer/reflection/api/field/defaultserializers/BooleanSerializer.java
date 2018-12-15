

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class BooleanSerializer implements FieldSerializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean object) {
        return new JsonPrimitive(object);
    }

    @Override
    public Boolean deserialize(JsonElement element) {
        return element.getAsBoolean();
    }
}
