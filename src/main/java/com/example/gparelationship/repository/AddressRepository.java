package com.example.gparelationship.repository;

import com.example.gparelationship.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByCityAndDistrictAndStreet(String city, String district, String street);

}
