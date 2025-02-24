package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.*;
import service.DatasetService;
import utility.JavaUtility;

import java.util.Map;

public class HomeController extends Controller {

    private final DatasetService datasetService;

    @Inject
    public HomeController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    public Result getAllDatasets() {
        JsonNode datasetsJson = Json.toJson(datasetService.getDatasets());
        Map<String, Object> response = JavaUtility.getResponse("success", "", "200", datasetsJson);
        return ok(Json.toJson(response));
    }



    public Result getDatasetById(String id) {
        return created(Json.toJson(JavaUtility.getResponse("success", "", "200", Json.toJson(Json.toJson(datasetService.getDatasetById(id))))));
    }

    public Result createDataset(Http.Request request) {
        String id = datasetService.createDataset(request);
        return created(Json.toJson(JavaUtility.getResponse("success", "", "201", Json.toJson("dataset created with id " + id))));
    }

    public Result updateDataset(String id, Http.Request request) {
        datasetService.updateDataset(id, request);
        return created(Json.toJson(JavaUtility.getResponse("success", "", "201", Json.toJson("dataset updated with id " + id))));
    }

    public Result deleteDatasetById(String id) {
        datasetService.deleteDataset(id);
        return ok(Json.toJson(JavaUtility.getResponse("success", "", "200", Json.toJson("dataset deleted with id " + id))));
    }

}
