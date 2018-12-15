package me.ggamer55.datahandler.api;

import me.ggamer55.datahandler.api.model.Model;

public interface AccessService<Complete extends Model> extends QueryService<Complete>, UpdateService<Complete>{
}
