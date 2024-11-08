package com.ds.ezcontract.controllers;

import com.ds.ezcontract.config.AppConstants;
import com.ds.ezcontract.payload.ContractDTO;
import com.ds.ezcontract.payload.ContractResponse;
import com.ds.ezcontract.services.ContractService;

import com.ds.ezcontract.util.AuthUtil;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ContractController {
    @Autowired
    private ContractService service;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/users/contract")
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contractDTO) {
        ContractDTO createdContract = service.addContract(contractDTO);
        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }


    @GetMapping("/admin/contracts")
    public ResponseEntity<?> getAllContracts(@RequestParam(name = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                         @RequestParam(name = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                         @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CONTRACTS_BY, required = false) String sortBy,
                                         @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ContractResponse contractResponse = service.getAllContracts(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(contractResponse, HttpStatus.OK);
    }

    @GetMapping("/users/contracts")
    public ResponseEntity<?> getUserContract()  { 
        String emailId = authUtil.loggedInEmail();
        
        List<ContractDTO> contractResponse = service.getAllContractsByUser(emailId);
        return new ResponseEntity<>(contractResponse, HttpStatus.OK);
    }
    

    @PutMapping("/users/contract/{id}")
    public ResponseEntity<?> updateContract(@Valid @RequestBody ContractDTO contractDTO, @PathVariable Long id) {
        ContractDTO updateContract = service.updateContract(contractDTO, id);
        return new ResponseEntity<>(updateContract, HttpStatus.OK);
    }

    @DeleteMapping("/users/contract/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
        ContractDTO contractDTO = service.deleteContract(id);
        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }
    
}
