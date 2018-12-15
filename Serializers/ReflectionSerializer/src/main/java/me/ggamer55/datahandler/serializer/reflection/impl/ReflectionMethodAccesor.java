

package me.ggamer55.datahandler.serializer.reflection.impl;

import me.ggamer55.datahandler.api.Serializable;
import me.ggamer55.datahandler.serializer.reflection.api.annotations.Setter;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.serializer.reflection.api.MethodAccesor;
import me.ggamer55.datahandler.serializer.reflection.api.MethodData;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReflectionMethodAccesor<Complete extends Model> implements MethodAccesor<Complete> {
    @Override
    public List<MethodData> methodGetters(Class<? extends Complete> modelClass) {
        Map<String, MethodData> methodGetters = new LinkedHashMap<>();
        for (Method method : modelClass.getMethods()) {
            Serializable serializable = method.getAnnotation(Serializable.class);

            if (serializable == null && !method.getName().equalsIgnoreCase("id")) {
                continue;
            }

            MethodData methodData;

            if (method.getName().equalsIgnoreCase("id") ) {
                methodData = new MethodData(method.getName(), method, "_id");
            } else {
                if (serializable.value().isEmpty()) {
                    methodData = new MethodData(method.getName(), method, method.getName());
                } else {
                    methodData = new MethodData(method.getName(), method, serializable.value());
                }

            }

            if (methodGetters.containsKey(methodData.getDatabaseFieldName())) {
                throw new RuntimeException("The model " + modelClass.getName() + " has 2 serializable methods with the same field name");
            }

            methodGetters.put(methodData.getDatabaseFieldName(), methodData);
        }

        return new LinkedList<>(methodGetters.values());
    }

    @Override
    public List<MethodData> methodSetters(Class<? extends Complete> clazz) {
        Map<String, MethodData> methodSetters = new LinkedHashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            Setter setterAnnotation = method.getAnnotation(Setter.class);

            if (setterAnnotation == null && !method.getName().equalsIgnoreCase("setId")) {
                continue;
            }

            MethodData methodData;

            if (method.getName().equalsIgnoreCase("setId") ) {
                methodData = new MethodData(method.getName(), method, "_id");
            } else {
                if (setterAnnotation.value().isEmpty()) {
                    methodData = new MethodData(method.getName(), method, method.getName());
                } else {
                    methodData = new MethodData(method.getName(), method, setterAnnotation.value());
                }
            }

            methodSetters.put(methodData.getDatabaseFieldName(), methodData);
        }
        return new LinkedList<>(methodSetters.values());
    }
}