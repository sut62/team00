package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"STUDENT_ID"}))
public class Student {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Pattern(regexp = "[BMD]\\d{7}")
    @Column(name = "STUDENT_ID")
    private String studentId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
