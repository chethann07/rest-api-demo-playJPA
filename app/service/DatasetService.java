package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import model.Datasets;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class DatasetService {

    private final EntityManager entityManager;

    @Inject
    public DatasetService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Datasets> getDatasets() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        List<Datasets> datasets = entityManager.createQuery("from Datasets", Datasets.class).getResultList();
        for (Datasets dataset : datasets) {
            entityManager.refresh(dataset);
        }
        entityManager.getTransaction().commit();
        return datasets;
    }

    public Datasets getDatasetById(String id) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        Datasets dataset = entityManager.find(Datasets.class, id);
        if (dataset == null) {
            throw new NotFoundException("dataset with id " + id + " not found");
        }
        entityManager.refresh(dataset);
        return entityManager.find(Datasets.class, id);
    }

    public void createDataset(Http.Request request) {
        JsonNode json = request.body().asJson();
        if(!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        if(entityManager.find(Datasets.class, json.get("id").asText()) != null) {
            throw new BadRequestException("dataset with id " + json.get("id").asText() + " already exists");
        }

//        if(json.get("routeConfig").asText().isBlank() || json.get("dataSchema").asText().isBlank() ||
//                json.get("createdBy").asText().isBlank() || json.get("updatedBy").asText().isBlank()) {
//            throw new BadRequestException("some of the required fields are missing");
//        }

        String status = json.get("status").asText().toUpperCase();
        try{
            Datasets.Status.valueOf(status);
        }catch(IllegalArgumentException e){
            throw new BadRequestException("invalid dataset status");
        }

        Datasets dataset = Json.fromJson(json, Datasets.class);
        entityManager.persist(dataset);
        entityManager.getTransaction().commit();
    }

    public void updateDataset(String id, Http.Request request) {
        Datasets existingDataset = getDatasetById(id);

        if (existingDataset == null) {
            throw new NullPointerException("dataset with id " + id + " not found");
        }

        JsonNode json = request.body().asJson();

        if (json.has("id")) {
            throw new BadRequestException("id should not be passed in update request");
        }
        if (json.has("createdBy")) {
            throw new BadRequestException("createdBy field should not be passed in update request");
        }
        if (!json.has("updatedBy") || !json.get("updatedBy").isTextual()) {
            throw new BadRequestException("updatedBy field must be a string");
        }

        Datasets dataset = Json.fromJson(json, Datasets.class);
        dataset.setId(id);

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(dataset);
        entityManager.getTransaction().commit();
    }


    public boolean deleteDataset(String id) {
        Datasets dataset = getDatasetById(id);
        if(dataset != null) {
            if(!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.remove(dataset);
            entityManager.getTransaction().commit();
            return true;
        }else{
            throw new NotFoundException("dataset with id " + id + " not found");
        }
    }
}
