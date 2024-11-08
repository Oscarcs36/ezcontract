package com.ds.ezcontract.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ds.ezcontract.config.AppConstants;
import com.ds.ezcontract.payload.ClauseDTO;
import com.ds.ezcontract.payload.ClauseResponse;
import com.ds.ezcontract.services.ClauseService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClauseController {
    @Autowired
    ClauseService service;

    @GetMapping("/public/clauses")
    public ResponseEntity<?> getAllClauses(@RequestParam(name = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                              @RequestParam(name = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                              @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CLAUSES_BY, required = false) String sortBy,
                                              @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ClauseResponse response = service.getAllClauses(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/clauses")
    public ResponseEntity<?> createClause(@Valid @RequestBody ClauseDTO clauseDTO) {
        ClauseDTO savedClauseDTO = service.createClause(clauseDTO);
        return new ResponseEntity<>(savedClauseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/clauses/{id}")
    public ResponseEntity<?> deleteClause(@PathVariable Long id){
            ClauseDTO deleted = service.deleteClause(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
    
    @PutMapping("/admin/clauses/{id}")
    public ResponseEntity<?> updateClause(@Valid @RequestBody ClauseDTO clauseDTO, @PathVariable Long id) {
        ClauseDTO savedClause = service.updateClause(clauseDTO, id);
        return new ResponseEntity<>(savedClause, HttpStatus.OK);
    }
}
