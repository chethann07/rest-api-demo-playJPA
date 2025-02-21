package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import model.Datasets;
import play.libs.Json;
import play.mvc.*;
import service.DatasetService;
import utility.JavaUtility;

public class HomeController extends Controller {

    private final DatasetService datasetService;

    @Inject
    public HomeController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    public Result getAllDatasets() {
        return ok(Json.toJson(datasetService.getDatasets()));
    }

    public Result getDatasetById(String id) {
        return ok(Json.toJson(datasetService.getDatasetById(id)));
    }

    public Result createDataset(Http.Request request) {
        datasetService.createDataset(request);
        return ok(Json.toJson(JavaUtility.getResponse("dataset created", "201")));
    }

    public Result updateDataset(String id, Http.Request request) {
        datasetService.updateDataset(id, request);
        return ok(Json.toJson(JavaUtility.getResponse("dataset updated", "201")));
    }


    public Result deleteDatasetById(String id) {
        if(datasetService.deleteDataset(id)){
            return ok(Json.toJson(JavaUtility.getResponse("dataset deleted with id " + id, "200")));
        }
        return ok(Json.toJson(JavaUtility.getResponse("dataset with id "+ id + " not found", "200")));
    }

}
