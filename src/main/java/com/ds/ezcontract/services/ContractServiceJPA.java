package com.ds.ezcontract.services;

import java.util.*;
import java.util.stream.Collectors;

import com.ds.ezcontract.models.User;
import com.ds.ezcontract.repositories.UserRepository;
import com.ds.ezcontract.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.ezcontract.exceptions.APIException;
import com.ds.ezcontract.exceptions.ResourceNotFoundException;
import com.ds.ezcontract.models.Clause;
import com.ds.ezcontract.models.Contract;
import com.ds.ezcontract.payload.ClauseDTO;
import com.ds.ezcontract.payload.ContractDTO;
import com.ds.ezcontract.payload.ContractResponse;
import com.ds.ezcontract.repositories.ClauseRepository;
import com.ds.ezcontract.repositories.ContractRepository;

@Service
public class ContractServiceJPA implements ContractService{

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClauseRepository clauseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil util;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContractDTO addContract(ContractDTO contractDTO) {
        Contract contract = modelMapper.map(contractDTO, Contract.class);

        Set<Long> clauseIds = contractDTO.getClausesId().stream()
            .map(ClauseDTO::getId) 
            .collect(Collectors.toSet());

        Set<Clause> clauses = new HashSet<>(clauseRepository.findAllById(clauseIds));

        User user = userRepository.findByEmail(util.loggedInEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", util.loggedInEmail()));

        contract.setClauses(clauses);
        contract.setUser(user);

        Contract savedContract = contractRepository.save(contract);

        return modelMapper.map(savedContract, ContractDTO.class);
    }

    @Override
public ContractResponse getAllContracts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    Sort sortByAndOrder = sortOrder.equals("asc") 
            ? Sort.by(sortBy).ascending()  
            : Sort.by(sortBy).descending();
    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Contract> page = contractRepository.findAll(pageDetails);

    List<Contract> contracts = page.getContent();

    if (contracts.isEmpty()) {
        throw new APIException("No contracts created");
    }

    modelMapper.typeMap(Contract.class, ContractDTO.class).addMappings(mapper -> {
        mapper.map(src -> src.getClauses() != null
                        ? src.getClauses().stream()
                        .map(Clause::getId)
                        .collect(Collectors.toSet())
                        : Collections.emptySet(),
                ContractDTO::setClausesId);
        });
    List<ContractDTO> contractDTOs = contracts.stream()
            .map(contract -> modelMapper.map(contract, ContractDTO.class))
            .toList();

    ContractResponse contractResponse = new ContractResponse();
    contractResponse.setContent(contractDTOs);
    contractResponse.setPageNumber(page.getNumber());
    contractResponse.setPageSize(page.getSize());
    contractResponse.setTotalElements(page.getTotalElements());
    contractResponse.setTotalPages(page.getTotalPages());
    contractResponse.setLastPage(page.isLast());

    return contractResponse;
}


    @Transactional
    @Override
    public ContractDTO updateContract(ContractDTO contractDTO, Long id) {
        Contract contractDb = contractRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contract", "id", id));
    
        contractDb.setOwnerName(contractDTO.getOwnerName());
        contractDb.setOwnerEmail(contractDTO.getOwnerEmail());
        contractDb.setRenterName(contractDTO.getRenterName());
        contractDb.setRenterEmail(contractDTO.getRenterEmail());
        contractDb.setStreet(contractDTO.getStreet());
        contractDb.setNumberHouse(contractDTO.getNumberHouse());
        contractDb.setSuburb(contractDTO.getSuburb());
        contractDb.setZip(contractDTO.getZip());
        contractDb.setState(contractDTO.getState());
        contractDb.setCountry(contractDTO.getCountry());
        contractDb.setStartContract(contractDTO.getStartContract());
        contractDb.setEndContract(contractDTO.getEndContract());
        contractDb.setCost(contractDTO.getCost());
    
        Set<Long> clauseIds = contractDTO.getClausesId().stream()
        .map(ClauseDTO::getId)  
        .collect(Collectors.toSet());

        Set<Clause> clauses = new HashSet<>(clauseRepository.findAllById(clauseIds));
        contractDb.setClauses(clauses);

        Contract contractSaved = contractRepository.save(contractDb);
        return modelMapper.map(contractSaved, ContractDTO.class);
    }

    @Override
    public ContractDTO deleteContract(Long id) {
        Contract contractDb = contractRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contract", "id", id));

        contractRepository.delete(contractDb);
        return modelMapper.map(contractDb, ContractDTO.class);
    }

    @Override
    public List<ContractDTO> getAllContractsByUser(String emailId) {
        List<Contract> contracts = contractRepository.findContractByEmail(emailId);
        if (contracts.isEmpty()) {
            throw new APIException("No contracts found for the user with email: " + emailId);
        }
        modelMapper.typeMap(Contract.class, ContractDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getClauses() != null
                            ? src.getClauses().stream()
                            .map(Clause::getId)
                            .collect(Collectors.toSet())
                            : Collections.emptySet(),
                    ContractDTO::setClausesId);
        });

        return contracts.stream()
            .map(contract -> modelMapper.map(contract, ContractDTO.class))
            .collect(Collectors.toList());
    }
}
