package handlers;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import utility.JavaUtility;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class GlobalErrorHandler implements HttpErrorHandler{
    @Override
    public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {

        if(statusCode == 405){
            return CompletableFuture.completedFuture(
                    Results.status(405, Json.toJson(JavaUtility.getResponse("requested method " + request.method() + " was not allowed", "405")))
            );
        }else if(statusCode == 404){
            return CompletableFuture.completedFuture(
                    Results.notFound(Json.toJson(JavaUtility.getResponse("requested URL " + request.uri() + " was not found", "404")))
            );
        }else{
            return CompletableFuture.completedFuture(
                    Results.status(statusCode, Json.toJson(JavaUtility.getResponse(message, String.valueOf(statusCode))))
            );
        }
    }

    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
//        exception.printStackTrace();
        if (exception instanceof BadRequestException) {
            return CompletableFuture.completedFuture(
                    Results.badRequest(Json.toJson(JavaUtility.getResponse(exception.getMessage(), "400")))
            );
        } else if (exception instanceof NullPointerException || exception instanceof NotFoundException) {
            return CompletableFuture.completedFuture(
                    Results.notFound(Json.toJson(JavaUtility.getResponse(exception.getMessage(), "404")))
            );
        } else {
            return CompletableFuture.completedFuture(
                    Results.internalServerError(Json.toJson(JavaUtility.getResponse("An unexpected error occurred", "500")))
            );
        }
    }
}
