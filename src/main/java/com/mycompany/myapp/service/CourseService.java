package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.UserNotActivatedException;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private CourseMapper courseMapper;

    public void enrollCourse(String userName, String courseName) {
        //TODO: implement enroll course logic
        UserCourse userCourse = getUserCourse(userName, courseName);
        Optional<UserCourse> optionalUserCourse = userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        optionalUserCourse.ifPresent(existingUserCourse -> {
            throw new IllegalArgumentException("UserCourse already exist: " + existingUserCourse.toString());
        });
        userCourseRepository.save(userCourse);
    }

    public List<CourseDTO> getAllCourses() {
        //TODO: implement get all courses logic
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    public List<CourseDTO> getStudentCourses(String userName) {
        //TODO: implement get student enrolled courses logic
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user: " + userName));

        List<UserCourse> userCourseList = userCourseRepository.findAllByUser(user);
        return userCourseList
            .stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.convert(course))
            .collect(Collectors.toList());
    }

    public void dropCourse(String userName, String courseName) {
        //TODO: implement drop course logic
        UserCourse userCourse = getUserCourse(userName, courseName);
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
    }

    private UserCourse getUserCourse(String userName, String courseName) {
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user: " + userName));

        Optional<Course> optionalCourse = courseRepository.findOneByCourseName(courseName);
        Course course = optionalCourse.orElseThrow(() -> new IllegalArgumentException("No such course: " + courseName));

        return new UserCourse(user, course);
    }
}
