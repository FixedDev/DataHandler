

package me.ggamer55.datahandler.serializer.reflection.api;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MethodData {
    private final String methodName;
    private final Method method;
    private final Class<?> returnType;

    private final String databaseFieldName;

    public MethodData(String methodName, Method method, String databaseFieldName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(methodName));
        Preconditions.checkNotNull(method);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(databaseFieldName));
        Preconditions.checkArgument(method.getName().equalsIgnoreCase(methodName), "MethodName doesn't equals with method.getName!!");

        this.methodName = methodName;
        this.method = method;
        this.returnType = method.getReturnType();
        this.databaseFieldName = databaseFieldName;
    }
}
