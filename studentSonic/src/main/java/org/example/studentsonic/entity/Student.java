package org.example.studentsonic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student implements Comparable<Student> {
    private String name;
    private int grade;

    // 오름차순을 조회하게끔 정렬을 하게 해준다, 객체끼리 비교해서 정수로 값을 내려준다.
    @Override
    public int compareTo(Student o) {
        return this.grade - o.grade;
    }


}
