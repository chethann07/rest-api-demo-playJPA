package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
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

        // Refresh each entity to ensure data is up-to-date from DB
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
        entityManager.refresh(dataset);
        return entityManager.find(Datasets.class, id);
    }

    public void createDataset(Http.Request request) {
        JsonNode json = request.body().asJson();
        Datasets dataset = Json.fromJson(json, Datasets.class);
        if(!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(dataset);
        entityManager.getTransaction().commit();
    }

    public void updateDataset(String id, Http.Request request) {
        if(getDatasetById(id) != null) {
            JsonNode json = request.body().asJson();
            if(json.has("id")){
                throw new IllegalArgumentException("dataset id cant be passed for update");
            }
            Datasets dataset = Json.fromJson(json, Datasets.class);
            dataset.setId(id);
            if(!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.merge(dataset);
            entityManager.getTransaction().commit();
        }
    }

    public boolean deleteDataset(String id) {
        Datasets dataset = entityManager.find(Datasets.class, id);
        if(dataset != null) {
            if(!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.remove(dataset);
            entityManager.getTransaction().commit();
            return true;
        }
        return false;
    }
}
