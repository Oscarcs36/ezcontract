package com.ds.ezcontract.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ds.ezcontract.models.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM contracts c WHERE c.user.email = ?1")
    List<Contract> findContractByEmail(String email);
}
