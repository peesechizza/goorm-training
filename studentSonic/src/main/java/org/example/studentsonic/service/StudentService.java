package org.example.studentsonic.service;

import lombok.RequiredArgsConstructor;
import org.example.studentsonic.entity.Student;
import org.example.studentsonic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service -> 비즈니스 로직 (가져온 데이터 가공, Response 객체에 담기, 객체 이동)
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    public Student addStudent(String name, int grade) {
        Student student = new Student(name, grade);
        studentRepository.add(student);
        return student;
    }

    public List<Student> getAll() {
        return studentRepository.getAll();
    }

    public List<Student> getGradeStudent(int grade) {
        return studentRepository.get(grade);
    }
}
