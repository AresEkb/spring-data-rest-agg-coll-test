package com.example.aggregatecollection;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "parents", collectionResourceRel = "parents")
public interface ParentRepository extends JpaRepository<Parent, UUID> {

}
