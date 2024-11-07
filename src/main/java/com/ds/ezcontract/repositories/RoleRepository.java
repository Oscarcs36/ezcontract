package com.ds.ezcontract.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.ezcontract.models.AppRole;
import com.ds.ezcontract.models.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long>  {
    Optional<Role> findByRoleName(AppRole roleName);
}
