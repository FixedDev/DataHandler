


package me.ggamer55.datahandler.api.request;

import com.google.common.reflect.TypeToken;
import me.ggamer55.datahandler.api.model.Model;

public interface ModelRequest<Complete extends Model> {

    default TypeToken<Complete> modelType(){
        return new TypeToken<Complete>(getClass()) {};
    }
}
