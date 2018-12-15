

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class DoubleSerializer implements FieldSerializer<Double> {
    @Override
    public JsonElement serialize(Double object) {
        return new JsonPrimitive(object);
    }

    @Override
    public Double deserialize(JsonElement element) {
        return element.getAsDouble();
    }
}
