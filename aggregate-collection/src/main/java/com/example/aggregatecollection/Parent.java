package com.example.aggregatecollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Child> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(Child child) {
        if (!children.contains(child)) {
            children.add(child);
            child.setParent(this);
        }
    }

    public void removeChild(Child child) {
        if (children.contains(child)) {
            children.remove(child);
            child.setParent(null);
        }
    }

}
