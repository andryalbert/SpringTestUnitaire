package com.example.spring.test.unitaire.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue
    private Long employeeId;

    @Column(nullable = false)
    @Size(max = 150)
    private String firstName;

    @Column(nullable = false)
    @Size(max = 150)
    private String lastName;
}
