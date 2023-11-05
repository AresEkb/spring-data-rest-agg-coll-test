package com.example.aggregatecollection;

import org.junit.jupiter.api.Test;

class MapParentRepositoryTests extends AbstractParentRepositoryTests {

    @Override
    protected String getCollectionUrlPath() {
        return getCollectionUrlPath(MapParent.class);
    }

    @Test
    @Override
    void aggregateCollectionIsCreated() {
        super.aggregateCollectionIsCreated();
    }

    @Test
    @Override
    void itemIsUpdated() {
        super.itemIsUpdated();
    }

    @Test
    @Override
    void firstItemIsAdded() {
        super.firstItemIsAdded();
    }

    @Test
    @Override
    void lastItemIsAdded() {
        super.lastItemIsAdded();
    }

    @Test
    @Override
    void firstItemIsDeleted() {
        super.firstItemIsDeleted();
    }

    @Test
    @Override
    void lastItemIsDeleted() {
        super.lastItemIsDeleted();
    }

    @Test
    @Override
    void itemsAreAddedDeletedUpdated() {
        super.itemsAreAddedDeletedUpdated();
    }

}
