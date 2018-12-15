

package me.ggamer55.datahandler.api.request;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import me.ggamer55.datahandler.api.model.Model;

import java.util.Objects;
import java.util.Set;

public class UpdateModelRequest<Complete extends Model> implements ModelRequest<Complete> {

    private Set<Complete> models;
    private TypeToken<Complete> modelType;

    public UpdateModelRequest(TypeToken<Complete> modelType, Set<Complete> models) {
        this.models = Objects.requireNonNull(models);
        Preconditions.checkArgument(!models.isEmpty());

        this.modelType = modelType;
    }

    public Set<Complete> models(){
        return ImmutableSet.copyOf(models);
    }

    @Override
    public TypeToken<Complete> modelType() {
        return modelType;
    }
}
