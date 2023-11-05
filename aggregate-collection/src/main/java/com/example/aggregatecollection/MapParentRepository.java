package com.example.aggregatecollection;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "map-parents", collectionResourceRel = "map-parents")
public interface MapParentRepository extends JpaRepository<MapParent, UUID> {

}
