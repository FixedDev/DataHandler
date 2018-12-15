

package me.ggamer55.datahandler.api;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListenableFuture;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.request.DeleteModelRequest;
import me.ggamer55.datahandler.api.request.UpdateModelRequest;
import me.ggamer55.datahandler.api.response.UpdateModelResponse;

import java.util.*;

public interface UpdateService<Complete extends Model> {

    ListenableFuture<UpdateModelResponse> update(UpdateModelRequest<Complete> request);

    ListenableFuture<UpdateModelResponse> delete(DeleteModelRequest<Complete> request);

    default ListenableFuture<UpdateModelResponse> updateMultiModel(Complete... models) {
        return update(new UpdateModelRequest<>(new TypeToken<Complete>() {
        }, Sets.newHashSet(models)));
    }

    default ListenableFuture<UpdateModelResponse> update(Complete model) {
        return update(new UpdateModelRequest<>(new TypeToken<Complete>() {
        }, Collections.singleton(model)));
    }

    default ListenableFuture<UpdateModelResponse> deleteMultiModel(String... ids) {
        return delete(new DeleteModelRequest<>(new TypeToken<Complete>() {
        }, Sets.newHashSet(ids)));
    }

    default ListenableFuture<UpdateModelResponse> deleteModel(String model) {
        return delete(new DeleteModelRequest<>(new TypeToken<Complete>() {
        }, Collections.singleton(model)));
    }

    default ListenableFuture<UpdateModelResponse> deleteAll() {
        return delete(new DeleteModelRequest<>(new TypeToken<Complete>() {
        }));
    }

}
