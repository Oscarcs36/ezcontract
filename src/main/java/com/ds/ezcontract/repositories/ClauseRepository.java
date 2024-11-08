package com.ds.ezcontract.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.ezcontract.models.Clause;

public interface ClauseRepository extends JpaRepository<Clause, Long>{
    Clause findByTitle(String title);
}
