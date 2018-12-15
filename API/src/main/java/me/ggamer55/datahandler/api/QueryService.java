

package me.ggamer55.datahandler.api;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListenableFuture;
import me.ggamer55.datahandler.api.model.Model;
import me.ggamer55.datahandler.api.request.FindModelRequest;
import me.ggamer55.datahandler.api.response.FindModelResponse;

import java.util.Collections;

public interface QueryService<Complete extends Model> {

    ListenableFuture<FindModelResponse<Complete>> find(FindModelRequest<Complete> findModelRequest);

    default ListenableFuture<FindModelResponse<Complete>> all() {
        return find(new FindModelRequest<Complete>(new TypeToken<Complete>() {
        }));
    }

    default ListenableFuture<FindModelResponse<Complete>> find(String id) {
        return find(new FindModelRequest<Complete>(new TypeToken<Complete>() {
        }, Collections.singleton(id)));
    }

    default ListenableFuture<FindModelResponse<Complete>> findMultiple(String... ids) {
        return find(new FindModelRequest<Complete>(new TypeToken<Complete>() {
        }, Sets.newHashSet(ids)));
    }

}
