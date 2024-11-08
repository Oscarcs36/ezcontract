package com.ds.ezcontract.services;

import com.ds.ezcontract.payload.ClauseDTO;
import com.ds.ezcontract.payload.ClauseResponse;

public interface ClauseService {
    ClauseResponse getAllClauses(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder);
    ClauseDTO createClause(ClauseDTO clauseDTO);
    ClauseDTO deleteClause(Long id);
    ClauseDTO updateClause(ClauseDTO clauseDTO, Long id);
}
