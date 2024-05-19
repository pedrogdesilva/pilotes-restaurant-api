package com.api.pilotes.persistence;

import com.api.pilotes.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(
            "SELECT c FROM Order c WHERE c.user.firstName LIKE concat('%', :searchName, '%') OR c.user.lastName LIKE concat('%', :searchName, '%')"
    )
    List<Order> findByUsername(String searchName);
}
