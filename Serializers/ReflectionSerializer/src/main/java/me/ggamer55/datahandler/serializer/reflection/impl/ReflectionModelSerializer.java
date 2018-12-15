

package me.ggamer55.datahandler.serializer.reflection.impl;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.model.serializer.ModelSerializer;
import me.ggamer55.datahandler.serializer.reflection.api.MethodAccesor;
import me.ggamer55.datahandler.serializer.reflection.api.MethodData;
import me.ggamer55.datahandler.serializer.reflection.api.field.CentralFieldSerializer;

import java.lang.reflect.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReflectionModelSerializer<Complete extends Model> implements ModelSerializer<Complete> {

    private MethodAccesor<Complete> methodAccesor;

    public ReflectionModelSerializer(MethodAccesor<Complete> fieldGetter) {
        Preconditions.checkNotNull(fieldGetter);
        methodAccesor = fieldGetter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonObject serialize(Complete model) {
        List<MethodData> modelGetters = methodAccesor.methodGetters((Class<? extends Complete>) model.getClass());

        CentralFieldSerializer fieldSerializer = CentralFieldSerializer.INSTANCE; //TODO: Make it pluggable

        JsonObject object = new JsonObject();

        modelGetters.forEach((methodData) -> {
            String fieldName = methodData.getDatabaseFieldName();
            Class<?> returnType = methodData.getMethod().getReturnType();

            Method method = methodData.getMethod();

            if (method.getParameterCount() > 0) {
                throw new RuntimeException("The method " + method.getName() + " in the class " + method.getDeclaringClass().getName() + " has 1 or more parameters(0 needed)");
            }

            if (returnType == Void.TYPE) {
                throw new RuntimeException("The method " + method.getName() + " in the class " + method.getDeclaringClass().getName() + " returns void");
            }

            if (!fieldSerializer.isSerializerAlreadyRegistered(returnType)) {
                throw new RuntimeException("The method " + method.getName() + " in the class " + method.getDeclaringClass().getName() + " returns a type not serializable (" + method.getReturnType().getName() + ')');
            }

            method.setAccessible(true);

            try {
                object.add(fieldName, fieldSerializer.serialize((Class<Object>) returnType, method.invoke(model)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to serialize field " + methodData.getDatabaseFieldName() + " in the model " + model.getClass().getName(), e);
            }
        });

        return object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Complete deserialize(JsonObject object, TypeToken<Complete> type) {
        List<MethodData> methodSetters = methodAccesor.methodSetters((Class<? extends Complete>) type.getRawType());
        Map<String, MethodData> methodGetters = new LinkedHashMap<>();
        methodAccesor.methodGetters((Class<? extends Complete>) type.getRawType()).forEach(methodData -> methodGetters.put(methodData.getDatabaseFieldName(), methodData));

        CentralFieldSerializer fieldSerializer = CentralFieldSerializer.INSTANCE; //TODO: Make it pluggable

        if (isInstantiable(type.getRawType())) {
            try {
                Complete complete = (Complete) type.getRawType().newInstance();

                methodSetters.forEach(methodData -> {

                    MethodData getterData = methodGetters.get(methodData.getDatabaseFieldName());

                    if (getterData == null) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to deserialize field " + methodData.getDatabaseFieldName() + " in the model " + type.getRawType().getName() + " because there is no getter for that field");
                        return;
                    }

                    JsonElement jsonElement = object.get(methodData.getDatabaseFieldName());
                    try {
                        Object deserializedField = fieldSerializer.deserialize(getterData.getReturnType(), jsonElement);

                        boolean isAccesible = methodData.getMethod().isAccessible();

                        methodData.getMethod().setAccessible(true);
                        methodData.getMethod().invoke(complete, deserializedField);
                        methodData.getMethod().setAccessible(isAccesible);

                        methodGetters.remove(methodData.getDatabaseFieldName());
                    } catch (Exception ex) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to deserialize field " + methodData.getDatabaseFieldName() + " in the model " + type.getRawType().getName(), ex);
                    }
                });

                if (!methodGetters.isEmpty()) {
                    throw new RuntimeException("The model " + type.getRawType().getName() + " can't be deserialized because the next fields \"" + String.join(", ", methodGetters.keySet()) + "\" does not have a setter method!");

                }

                return complete;
            } catch (InstantiationException | IllegalAccessException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to deserialize the model " + type.getRawType().getName(), e);
            }

        }
        throw new RuntimeException("The model " + type.getRawType().getName() + " is not instantiable or a interface(Maybe an abstract class?)");
    }

    private boolean isInstantiable(Class<?> type) {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) return false;
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
