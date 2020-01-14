package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StudentTestCase {
    private Validator validator;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void B6000000_testStudentIdOKWith7Digits() {
        Student student = new Student();
        student.setStudentId("B5703234");
        student.setFirstName("Tanapon");
        student.setLastName("Kongjaroensuk");

        studentRepository.saveAndFlush(student);
        Optional<Student> check = studentRepository.findById(student.getId());
        assertEquals("B5703234", check.get().getStudentId());
        assertEquals("Tanapon", check.get().getFirstName());
        assertEquals("Kongjaroensuk", check.get().getLastName());
    }

    @Test
    void B6000000_testFirstNameNotNull() {
        Student student = new Student();
        student.setStudentId("B5703234");
        student.setFirstName(null);
        student.setLastName("Kongjaroensuk");

        Set<ConstraintViolation<Student>> result = validator.validate(student);


        assertEquals(1, result.size());

        ConstraintViolation<Student> message = result.iterator().next();
        assertEquals("must not be null", message.getMessage());
        assertEquals("firstName",message.getPropertyPath().toString());

    }

    @Test
    void B6000000_testLastNameNotNUll() {
        Student student = new Student();
        student.setStudentId("B5703234");
        student.setFirstName("Tanapon");
        student.setLastName(null);

        Set<ConstraintViolation<Student>> result = validator.validate(student);


        assertEquals(1, result.size());

        ConstraintViolation<Student> message = result.iterator().next();
        assertEquals("must not be null", message.getMessage());
        assertEquals("lastName",message.getPropertyPath().toString());
    }

    @Test
    void B6000000_testStudentIdFirstCharacterA(){
        Student student = new Student();
        student.setStudentId("A1234567");
        student.setFirstName("Tanapon");
        student.setLastName("Kongjaroensuk");

        Set<ConstraintViolation<Student>> result = validator.validate(student);

        assertEquals(1, result.size());
        ConstraintViolation<Student> message = result.iterator().next();
        assertEquals("must match \"[BMD]\\d{7}\"",message.getMessage());
        assertEquals("studentId",message.getPropertyPath().toString());
    }

    @Test
    void B6000000_testStudentIdFirstCharacterC(){
        Student student = new Student();
        student.setStudentId("C1234567");
        student.setFirstName("Tanapon");
        student.setLastName("Kongjaroensuk");

        Set<ConstraintViolation<Student>> result = validator.validate(student);

        assertEquals(1, result.size());
        ConstraintViolation<Student> message = result.iterator().next();
        assertEquals("must match \"[BMD]\\d{7}\"",message.getMessage());
        assertEquals("studentId",message.getPropertyPath().toString());
    }
}

