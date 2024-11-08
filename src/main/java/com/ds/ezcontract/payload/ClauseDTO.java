package com.ds.ezcontract.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClauseDTO {
    private Long id;
    private String title;
    private String description;
}
