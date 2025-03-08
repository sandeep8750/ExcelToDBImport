package com.sandeep.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "designation", length = 50)
    private String designation;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "status", length = 20)
    private String status;  // e.g., "Active", "Resigned", "On Leave"
}
