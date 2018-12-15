

package me.ggamer55.datahandler.serializer.reflection.api.field;

import com.google.common.primitives.Primitives;
import com.google.gson.JsonElement;
import me.ggamer55.datahandler.serializer.reflection.api.field.defaultserializers.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum CentralFieldSerializer {
    INSTANCE;

    private ConcurrentMap<Class<?>, FieldSerializer> registeredSerializers;

    CentralFieldSerializer() {
        registeredSerializers = new ConcurrentHashMap<>();

        registeredSerializers.put(Boolean.class, new BooleanSerializer());
        registeredSerializers.put(Double.class, new DoubleSerializer());
        registeredSerializers.put(Float.class, new FloatSerializer());
        registeredSerializers.put(Integer.class, new IntegerSerializer());
        registeredSerializers.put(Long.class, new LongSerializer());
        registeredSerializers.put(String.class, new StringSerializer());

        // Just in case that unwrapping doesn't work

        registeredSerializers.put(Boolean.TYPE, new BooleanSerializer());
        registeredSerializers.put(Double.TYPE, new DoubleSerializer());
        registeredSerializers.put(Float.TYPE, new FloatSerializer());
        registeredSerializers.put(Integer.TYPE, new IntegerSerializer());
        registeredSerializers.put(Long.TYPE, new LongSerializer());
    }

    @SuppressWarnings("unchecked")
    public synchronized <O> JsonElement serialize(Class<O> clazz, O object) {
        clazz = Primitives.unwrap(clazz);

        if (clazz != Primitives.unwrap(object.getClass())) {
            throw new IllegalArgumentException("The clazz argument isn't the same class of the object argument");
        }

        FieldSerializer<O> fieldSerializer = registeredSerializers.get(clazz);

        if (fieldSerializer == null) {
            throw new IllegalStateException("There isn't any serializer for the class " + clazz.getName());
        }

        return fieldSerializer.serialize(object);
    }

    @SuppressWarnings("unchecked")
    public synchronized <O> O deserialize(Class<O> clazz, JsonElement object) throws Exception {
        clazz = Primitives.unwrap(clazz);

        FieldSerializer<O> fieldSerializer = registeredSerializers.get(clazz);

        if (fieldSerializer == null) {
            throw new IllegalStateException("There isn't any serializer for the class " + clazz.getName());
        }

        return fieldSerializer.deserialize(object);
    }

    public synchronized <O> void registerSerializer(Class<O> clazz, FieldSerializer<O> fieldSerializer){
        clazz = Primitives.unwrap(clazz);

        if(registeredSerializers.containsKey(clazz)){
            throw new IllegalStateException("Already registered field serializer!");
        }

        registeredSerializers.put(clazz,fieldSerializer);
    }

    public synchronized void unregisterSerializer(Class<?> clazz){
        clazz = Primitives.unwrap(clazz);

        if(!registeredSerializers.containsKey(clazz)){
            throw new IllegalStateException("There isn't a field serializer for the class " + clazz.getName());
        }

        registeredSerializers.remove(clazz);
    }

    public synchronized boolean isSerializerAlreadyRegistered(Class<?> clazz){
        clazz = Primitives.unwrap(clazz);

        return registeredSerializers.containsKey(clazz);
    }
}
