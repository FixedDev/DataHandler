

package me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.ggamer55.datahandler.serializer.reflection.api.field.FieldSerializer;

public class LongSerializer implements FieldSerializer<Long> {
    @Override
    public JsonElement serialize(Long object) {
        return new JsonPrimitive(object);
    }

    @Override
    public Long deserialize(JsonElement element) {
        return element.getAsLong();
    }
}
