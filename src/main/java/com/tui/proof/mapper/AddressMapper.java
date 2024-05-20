package com.tui.proof.mapper;

import com.tui.proof.dto.AddressDTO;
import com.tui.proof.persistence.model.Address;

/**
 * @author Pedro Silva on 19/05/2024
 */
public class AddressMapper {

    public static Address getAddress(AddressDTO dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setStreet(dto.getStreet());
        address.setPostcode(dto.getPostcode());
        return address;
    }

    public static AddressDTO getAddress(Address address) {
        return new AddressDTO(
                address.getStreet(),
                address.getPostcode(),
                address.getCity(),
                address.getCountry()
        );
    }

}
