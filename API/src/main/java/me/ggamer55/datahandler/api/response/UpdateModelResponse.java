package me.ggamer55.datahandler.api.response;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UpdateModelResponse {
    @Getter
    private int saved = 0;
    @Getter
    private int deleted = 0;
    @Getter
    private int failed = 0;

    // id, errors
    private Map<String, List<String>> errors;

    private String cachedFormattedErrors;

    public UpdateModelResponse(int saved, int deleted, int failed, Map<String, List<String>> errors) {
        this.saved = saved;
        this.deleted = deleted;
        this.failed = failed;
        this.errors = errors;
    }

    public static UpdateModelResponse EMPTY = new UpdateModelResponse(0, 0, 0, new LinkedHashMap<>());

    public boolean sucess() {
        return failed <= 1;
    }

    public Optional<String> error() {
        return Optional.ofNullable(sucess() ? null : formattedErrors());
    }

    public String formattedErrors() {
        if (cachedFormattedErrors == null) {
            StringBuilder formattedErrors = new StringBuilder(100);
            formattedErrors.append("There are ").append(failed).append(" errors").append(":\n");

            errors.forEach((id, errors) -> {
               formattedErrors.append("id: ").append(id);
               formattedErrors.append(" errors:");
               errors.forEach(error -> {
                   formattedErrors.append("\n ").append(error);
               });
            });

            cachedFormattedErrors = formattedErrors.toString();
        }
        return cachedFormattedErrors;
    }
}
