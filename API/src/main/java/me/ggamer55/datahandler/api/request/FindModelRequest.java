

package me.ggamer55.datahandler.api.request;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import me.ggamer55.datahandler.api.model.Model;

import java.util.Objects;
import java.util.Set;

public class FindModelRequest<Complete extends Model> implements ModelRequest<Complete> {
    private Set<String> ids;
    private TypeToken<Complete> typeToken;

    public FindModelRequest(Class<Complete> clazz){
        this(TypeToken.of(clazz));
    }

    public FindModelRequest(TypeToken<Complete> typeToken){
        this(typeToken, Sets.newHashSet());
    }

    public FindModelRequest(TypeToken<Complete> typeToken, Set<String> ids) {
        this.ids = Objects.requireNonNull(ids);
        this.typeToken = typeToken;
    }

    public Set<String> modelIds(){
        return ImmutableSet.copyOf(ids);
    }

    @Override
    public TypeToken<Complete> modelType() {
        return typeToken;
    }
}
