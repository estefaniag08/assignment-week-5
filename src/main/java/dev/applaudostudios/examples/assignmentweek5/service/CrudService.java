package dev.applaudostudios.examples.assignmentweek5.service;

import dev.applaudostudios.examples.assignmentweek5.common.dto.IEntityDTO;

import java.util.Optional;

public interface CrudService<T extends IEntityDTO, K> {

    T createEntity(T entity);
    T updateEntity(T entity);
    Optional<T> getEntity(K key);
    void deleteEntity(K key);

}
