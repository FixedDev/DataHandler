

package me.ggamer55.datahandler.api.exception;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(Class<?> requestType, String message) {
        super("The request of type " + requestType.getName() + " is invalid, message: " + message);
    }
}
