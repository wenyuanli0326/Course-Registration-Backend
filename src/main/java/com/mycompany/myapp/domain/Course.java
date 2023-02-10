package com.mycompany.myapp.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "course")
@Data //lombok help writing getter/setter
public class Course {

    @Id // primary key  is id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    @Column(name = "id") // tell which column is the id column
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_location")
    private String courseLocation;

    @Column(name = "course_content")
    private String courseContent;

    @Column(name = "teacher_id")
    private Long teacherId;
}
