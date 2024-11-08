package com.ds.ezcontract.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "clauses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Title name must be contain at least 5 characters")
    private String title;

    @NotNull
    @Size(min = 50, message = "Clauses name must be contain at least 50 characters")
    private String description;

    @ManyToMany(mappedBy = "clauses", cascade = CascadeType.ALL)
    private Set<Contract> contract = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clause clause = (Clause) o;
        return Objects.equals(id, clause.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
