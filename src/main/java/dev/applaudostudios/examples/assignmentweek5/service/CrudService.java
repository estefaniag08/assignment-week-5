package dev.applaudostudios.examples.assignmentweek5.service;

import dev.applaudostudios.examples.assignmentweek5.common.dto.IEntityDTO;

import java.util.Optional;

public interface CrudService<T extends IEntityDTO, K> {

    void createEntity(T entity);
    void updateEntity(T entity);
    Optional<T> getEntity(K key);
    void deleteEntity(K key);

}
