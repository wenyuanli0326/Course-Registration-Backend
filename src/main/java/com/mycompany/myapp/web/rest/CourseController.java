package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.dto.CourseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CourseController {

    private CourseService courseService;

    /**
     * 1. Requirements: 学生选课
     * 2. Http Method: POST (create)
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 201
     * 5. Request body: path variable - {courseName}
     * 6. Response body: None (void)
     * 7. Request Header: JWT Token
     */
    @PostMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollCourse(@PathVariable String courseName) {
        String userName = getUserName();
        courseService.enrollCourse(userName, courseName);
    }

    /**
     * 1. Requirements: 列出所有课程
     * 2. Http Method: GET
     * 3. URL: /courses
     * 4. HTTP status code: 200
     * 5. Request body: None
     * 6. Response body: List<CourseDTO>
     * 7. Request Header: JWT Token
     */
    @GetMapping(path = "/courses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * 1. Requirements: 列出学生所有已选课程
     * 2. Http Method: GET
     * 3. URL: /student/courses
     * 4. HTTP status code: 200
     * 5. Request body: None
     * 6. Response body: List<CourseDTO>
     * 7. Request Header: JWT Token
     */
    @GetMapping(path = "/student/courses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CourseDTO> getStudentCourses() {
        String userName = getUserName();
        return courseService.getStudentCourses(userName);
    }

    /**
     * 1. Requirements: 提供学生drop课程功能
     * 2. Http Method: DELETE
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 200 / 204
     * 5. Request body: courseName (pathVariable)
     * 6. Response body: None (void)
     * 7. Request Header: JWT Token
     */
    @DeleteMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropCourse(@PathVariable String courseName) {
        String userName = getUserName();
        courseService.dropCourse(userName, courseName);
    }

    /**
     * Extract username from JWT token
     * @return
     */
    private String getUserName() {
        return SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Username not found");
            });
    }
}
