

package me.ggamer55.datahandler.api.request;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import me.ggamer55.datahandler.api.model.Model;

import java.util.Objects;
import java.util.Set;

public class DeleteModelRequest<Complete extends Model> implements ModelRequest<Complete> {
    private Set<String> ids;
    private TypeToken<Complete> typeToken;

    public DeleteModelRequest(Class<Complete> clazz){
        this(TypeToken.of(clazz));
    }

    public DeleteModelRequest(TypeToken<Complete> typeToken){
        this(typeToken, Sets.newHashSet());
    }

    public DeleteModelRequest(TypeToken<Complete> typeToken, Set<String> ids) {
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
