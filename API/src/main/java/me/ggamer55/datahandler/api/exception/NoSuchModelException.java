

package me.ggamer55.datahandler.api.exception;

import com.google.common.base.Joiner;

import java.util.Set;

public class NoSuchModelException extends Exception {
    public NoSuchModelException(Set<String> id, String message) {
        super("Model with id's " + Joiner.on(" ").join(id) + " not found, message: " + message);


    }

}
