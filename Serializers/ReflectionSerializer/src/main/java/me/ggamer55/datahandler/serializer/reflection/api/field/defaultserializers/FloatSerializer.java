

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class FloatSerializer implements FieldSerializer<Float> {
    @Override
    public JsonElement serialize(Float object) {
        return new JsonPrimitive(object);
    }

    @Override
    public Float deserialize(JsonElement element) {
        return element.getAsFloat();
    }
}
