package model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Datasets {

    @Id
    @Column(name = "id")
    private String id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data_schema", columnDefinition = "jsonb")
    private Map<String, Object> dataSchema;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "route_config", columnDefinition = "jsonb")
    private Map<String, Object> routeConfig;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Status {
        LIVE,
        DRAFT,
        RETIRED
    }

    public Datasets() {}

    public Datasets(String id, Map<String, Object> dataSchema, Map<String, Object> routeConfig, Status status, String createdBy, String updatedBy) {
        this.id = id;
        this.dataSchema = dataSchema;
        this.routeConfig = routeConfig;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Datasets(Map<String, Object> dataSchema, Map<String, Object> routeConfig, Status status, String createdBy, String updatedBy) {
        this.dataSchema = dataSchema;
        this.routeConfig = routeConfig;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(Map<String, Object> dataSchema) {
        this.dataSchema = dataSchema;
    }

    public Map<String, Object> getRouteConfig() {
        return routeConfig;
    }

    public void setRouteConfig(Map<String, Object> routeConfig) {
        this.routeConfig = routeConfig;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Datasets{" +
                "id='" + id + '\'' +
                ", dataSchema=" + dataSchema +
                ", routeConfig=" + routeConfig +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
