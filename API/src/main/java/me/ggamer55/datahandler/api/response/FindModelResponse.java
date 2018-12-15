package me.ggamer55.datahandler.api.response;

import me.ggamer55.datahandler.api.model.Model;

import java.util.Set;

public interface FindModelResponse<Complete extends Model> {
    Set<Complete> modelsFound();
}
