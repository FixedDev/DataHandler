package me.ggamer55.datahandler.api;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.request.DeleteModelRequest;
import me.ggamer55.datahandler.api.request.UpdateModelRequest;
import me.ggamer55.datahandler.api.response.UpdateModelResponse;

public class NullAccessService<Complete extends Model> extends NullQueryService<Complete> implements AccessService<Complete> {
    @Override
    public ListenableFuture<UpdateModelResponse> update(UpdateModelRequest<Complete> request) {
        return Futures.immediateFuture(UpdateModelResponse.EMPTY);
    }

    @Override
    public ListenableFuture<UpdateModelResponse> delete(DeleteModelRequest<Complete> request) {
        return Futures.immediateFuture(UpdateModelResponse.EMPTY);
    }
}
