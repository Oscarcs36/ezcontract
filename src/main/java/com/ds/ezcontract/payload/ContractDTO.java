package com.ds.ezcontract.payload;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String ownerName;
    private String ownerEmail;
    private String renterName;
    private String renterEmail;
    private String street;
    private String numberHouse;
    private String suburb;
    private String zip;
    private String state;
    private String country;
    private LocalDate startContract;
    private LocalDate endContract;
    private Integer cost;
    private Set<ClauseDTO> clausesId = new HashSet<>();
}
