package co.yusufavci.employeerecord.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntityAudit implements Serializable {

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    /**
     * Sets createdAt before insert
     */
    @PrePersist
    public void setCreationTime() {
        this.createdAt = Instant.now();
    }

    /**
     * Sets updatedAt before update
     */
    @PreUpdate
    public void setChangeTime() {
        this.updatedAt = Instant.now();
    }

    @PreRemove
    public void setDeleteTime() {
        this.deletedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseEntityAudit that = (BaseEntityAudit) o;
        return Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(deletedAt, that.deletedAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "BaseEntityAudit{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                "} " + super.toString();
    }
}