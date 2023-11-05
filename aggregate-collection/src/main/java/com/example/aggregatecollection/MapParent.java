package com.example.aggregatecollection;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

@Entity
public class MapParent {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey
    private Map<UUID, MapChild> children = new LinkedHashMap<>();

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

    public Collection<MapChild> getChildren() {
        return children.values();
    }

    public void setChildren(Collection<MapChild> children) {
        this.children.clear();
        for (var child : children) {
            addChild(child);
        }
    }

    public void addChild(MapChild child) {
        if (!children.containsValue(child)) {
            children.put(child.getId(), child);
            child.setParent(this);
        }
    }

    public void removeChild(MapChild child) {
        if (children.containsValue(child)) {
            children.remove(child.getId(), child);
            child.setParent(null);
        }
    }

}
