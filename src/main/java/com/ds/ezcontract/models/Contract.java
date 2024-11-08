package com.ds.ezcontract.models;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "contracts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Owner's name must contain at least 5 characters")
    private String ownerName;

    @Size(min = 5, message = "Owner's email must contain at least 5 characters")
    private String ownerEmail;

    @Size(min = 5, message = "Renter's name must contain at least 5 characters")
    private String renterName;

    @Size(min = 5, message = "Renter's email must contain at least 5 characters")
    private String renterEmail;

    @Size(min = 5, message = "Street must contain at least 5 characters")
    private String street;

    @Size(min = 1, message = "Number's house must contain at least 1 characters")
    private String numberHouse;

    @Size(min = 4, message = "Suburb must contain at least 4 characters")
    private String suburb;

    @Size(min = 5, message = "Zip must contain at least 5 characters")
    private String zip;

    @Size(min = 3, message = "State must contain at least 3 characters")
    private String state;

    @Size(min = 5, message = "Country must contain at least 5 characters")
    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startContract;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endContract;

    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "contract_clause",
        joinColumns = @JoinColumn(name = "contract_id"),
        inverseJoinColumns = @JoinColumn(name = "clause_id")
    )
    private Set<Clause> clauses = new HashSet<>();
}
