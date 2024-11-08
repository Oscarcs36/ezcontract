package com.ds.ezcontract.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ds.ezcontract.exceptions.APIException;
import com.ds.ezcontract.exceptions.ResourceNotFoundException;
import com.ds.ezcontract.models.Clause;
import com.ds.ezcontract.payload.ClauseDTO;
import com.ds.ezcontract.payload.ClauseResponse;
import com.ds.ezcontract.repositories.ClauseRepository;

@Service
public class ClauseServiceJPA implements ClauseService{
    @Autowired
    private ClauseRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClauseResponse getAllClauses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equals("asc") 
                ? Sort.by(sortBy).ascending()  
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Clause> page = repository.findAll(pageDetails);

        List<Clause> clauses = page.getContent();
        if(clauses.isEmpty())
            throw new APIException("No clauses created");

        List<ClauseDTO> clausesDTOs = clauses.stream()
                .map(clause -> modelMapper.map(clause, ClauseDTO.class))
                .toList();

        ClauseResponse clauseResponse = new ClauseResponse();
        clauseResponse.setContent(clausesDTOs);
        clauseResponse.setPageNumber(page.getNumber());
        clauseResponse.setPageSize(page.getSize());
        clauseResponse.setTotalElements(page.getTotalElements());
        clauseResponse.setTotalPages(page.getTotalPages());
        clauseResponse.setLastPage(page.isLast());
        
        return clauseResponse;
    }

    @Override
    public ClauseDTO createClause(ClauseDTO clauseDTO) {
        Clause clause = modelMapper.map(clauseDTO, Clause.class);
        Clause checkClause = repository.findByTitle(clause.getTitle());

        if(checkClause != null)
            throw new APIException("Clause with title: " + clause.getTitle() + " already exists");

        Clause savedClause = repository.save(clause);
        return modelMapper.map(savedClause, ClauseDTO.class);        
    }

    @Override
    public ClauseDTO deleteClause(Long id) {
        Clause clause = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clause", "id", id));
        
        repository.delete(clause);
        return modelMapper.map(clause, ClauseDTO.class);
    }

    @Override
    public ClauseDTO updateClause(ClauseDTO clauseDTO, Long id) {
        Clause savedClause = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clause", "id", id));

        Clause clause = modelMapper.map(clauseDTO, Clause.class);
        clause.setId(id);
        savedClause = repository.save(clause);
        return modelMapper.map(savedClause, ClauseDTO.class);
    }

}
