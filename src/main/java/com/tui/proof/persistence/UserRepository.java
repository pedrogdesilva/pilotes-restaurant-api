package com.tui.proof.persistence;

import com.tui.proof.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
