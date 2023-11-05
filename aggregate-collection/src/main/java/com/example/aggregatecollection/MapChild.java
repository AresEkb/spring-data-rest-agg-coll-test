package com.example.aggregatecollection;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "name" }) })
public class MapChild {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MapParent parent;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MapParent getParent() {
        return parent;
    }

    public void setParent(MapParent parent) {
        if (this.parent != parent) {
            if (this.parent != null) {
                this.parent.removeChild(this);
            }
            this.parent = parent;
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }
    }

}
