

package me.ggamer55.datahandler.api;

import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import me.ggamer55.datahandler.api.AccessService;
import me.ggamer55.datahandler.api.UpdateService;

import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.model.serializer.ModelSerializer;
import me.ggamer55.datahandler.api.request.DeleteModelRequest;
import me.ggamer55.datahandler.api.request.FindModelRequest;
import me.ggamer55.datahandler.api.request.UpdateModelRequest;
import me.ggamer55.datahandler.api.response.FindModelResponse;
import me.ggamer55.datahandler.api.response.UpdateModelResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class AbstractAccessService<Complete extends Model> implements AccessService<Complete> {

    private ModelSerializer<Complete> modelSerializer;
    private ListeningExecutorService executorService;

    /* Query Service */

    @Override
    public ListenableFuture<FindModelResponse<Complete>> find(FindModelRequest<Complete> findModelRequest) {
        Set<String> modelIds = findModelRequest.modelIds();
        TypeToken<Complete> modelType = findModelRequest.modelType();

        return executorService.submit(() -> {
            Set<JsonObject> notSerializedModels = new HashSet<>();

            if(modelIds.isEmpty()){
                notSerializedModels.addAll(getAll());
            } else {
                notSerializedModels.addAll(bulkGet(modelIds));
            }

            Set<Complete> models = notSerializedModels.stream().map((complete) -> modelSerializer.deserialize(complete,modelType)).collect(Collectors.toSet());

            return () -> models;
        });
    }

    protected abstract Set<JsonObject> bulkGet(Set<String> id);

    protected abstract Set<JsonObject> getAll();

    /* Update service */

    @Override
    public ListenableFuture<UpdateModelResponse> update(UpdateModelRequest<Complete> request) {
        Set<Complete> toBeUpdated = request.models();

        return executorService.submit(() -> {
            if (toBeUpdated.isEmpty()) {
                return UpdateModelResponse.EMPTY;
            }

            Set<JsonObject> serializedModels = toBeUpdated.stream().map(modelSerializer::serialize).collect(Collectors.toSet());

            return bulkUpdate(serializedModels);
        });
    }

    @Override
    public ListenableFuture<UpdateModelResponse> delete(DeleteModelRequest<Complete> request) {
        Set<String> toBeDeleted = request.modelIds();

        return executorService.submit(() -> {
            if (request.modelIds().isEmpty()) {
                return allDelete();
            }

            return bulkDelete(toBeDeleted);
        });
    }

    protected abstract UpdateModelResponse bulkUpdate(Set<JsonObject> update);

    protected abstract UpdateModelResponse bulkDelete(Set<String> ids);

    protected abstract UpdateModelResponse allDelete();
}
