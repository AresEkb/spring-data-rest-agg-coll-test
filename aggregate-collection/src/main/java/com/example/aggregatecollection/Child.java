package com.example.aggregatecollection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "name" }) })
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    @ManyToOne
    private Parent parent;

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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
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
