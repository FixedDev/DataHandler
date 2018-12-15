

package me.ggamer55.datahandler.serializer.reflection.api;

import me.ggamer55.datahandler.api.model.Model;

import java.util.List;

public interface MethodAccesor<Complete extends Model> {
    List<MethodData> methodGetters(Class<? extends Complete> model);
    List<MethodData> methodSetters(Class<? extends Complete> clazz);
}
