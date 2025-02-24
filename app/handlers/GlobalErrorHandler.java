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
                    Results.status(405, Json.toJson(JavaUtility.getResponse("failure", "requested method " + request.method() + " is not allowed", "405", null))
            ));
        }else if(statusCode == 404){
            return CompletableFuture.completedFuture(
                    Results.status(404, Json.toJson(JavaUtility.getResponse("failure", "requested URL " + request.uri() + " was not found", "404", null))
            ));
        }else{
            return CompletableFuture.completedFuture(
                    Results.status(statusCode, Json.toJson(JavaUtility.getResponse("failure", message, Integer.toString(statusCode), null))
            ));
        }
    }

    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
//        exception.printStackTrace();
        if (exception instanceof BadRequestException || exception instanceof IllegalArgumentException) {
            return CompletableFuture.completedFuture(
                    Results.badRequest(Json.toJson(JavaUtility.getResponse("failure", exception.getMessage(), "400", null)))
            );
        } else if (exception instanceof NullPointerException || exception instanceof NotFoundException) {
            return CompletableFuture.completedFuture(
                    Results.badRequest(Json.toJson(JavaUtility.getResponse("failure", exception.getMessage(), "404", null)))
            );
        } else {
            return CompletableFuture.completedFuture(
                    Results.badRequest(Json.toJson(JavaUtility.getResponse("failure", exception.getMessage(), "400", null)))
            );
        }
    }
}
