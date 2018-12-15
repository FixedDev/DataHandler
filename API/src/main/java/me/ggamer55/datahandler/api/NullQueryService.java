

package me.ggamer55.datahandler.api;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import me.ggamer55.datahandler.api.exception.NoSuchModelException;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.request.FindModelRequest;
import me.ggamer55.datahandler.api.response.FindModelResponse;

public class NullQueryService<Complete extends Model> implements QueryService<Complete> {
    @Override
    public ListenableFuture<FindModelResponse<Complete>> find(FindModelRequest<Complete> findModelRequest) {
        return Futures.immediateFailedFuture(new NoSuchModelException(findModelRequest.modelIds(), "Query service not initialized!"));
    }
}
