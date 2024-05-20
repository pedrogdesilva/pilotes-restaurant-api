package com.tui.proof.persistence;

import com.tui.proof.persistence.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
