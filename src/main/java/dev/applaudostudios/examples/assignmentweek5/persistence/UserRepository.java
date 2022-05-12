package dev.applaudostudios.examples.assignmentweek5.persistence;

import dev.applaudostudios.examples.assignmentweek5.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    User findByEmail(String email);
}
