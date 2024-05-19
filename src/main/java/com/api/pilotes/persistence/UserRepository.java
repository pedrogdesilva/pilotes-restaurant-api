package com.api.pilotes.persistence;

import com.api.pilotes.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
