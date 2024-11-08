package com.ds.ezcontract.services;

import java.util.List;

import com.ds.ezcontract.payload.ContractDTO;
import com.ds.ezcontract.payload.ContractResponse;


public interface ContractService {
    ContractDTO addContract(ContractDTO contractDTO);
    ContractResponse getAllContracts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ContractDTO updateContract(ContractDTO contractDTO, Long id);
    ContractDTO deleteContract(Long id);
    List<ContractDTO> getAllContractsByUser(String emailId);
}
