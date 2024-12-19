package org.example.capstone3.Service;

import lombok.AllArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.BookingCourse;
import org.example.capstone3.Model.Course;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.BookingCourseOutDTO;
import org.example.capstone3.Repository.BookingCourseRepository;
import org.example.capstone3.Repository.CourseRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingCourseService {

    private final BookingCourseRepository bookingCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<BookingCourseOutDTO> getAllBookingCourses() {
        List<BookingCourse> bookingCourses = bookingCourseRepository.findAll();
        List<BookingCourseOutDTO> bookingCourseOutDTOs = new ArrayList<>();
        for (BookingCourse bookingCourse : bookingCourses) {
            bookingCourseOutDTOs.add(new BookingCourseOutDTO(bookingCourse.getBookingDate(),bookingCourse.getCourseStartDate(),bookingCourse.getCourseEndDate()));
        }
        return bookingCourseOutDTOs;
    }

    public String bookingCourse(Integer userId, Integer courseId,BookingCourse bookingCourse) {
        // Step 1: Validate course existence
        Course course = courseRepository.findCourseById(courseId);
        if(course==null){ throw new ApiException("Course not found!");}

        // Step 2: Check trainer availability
        boolean isUnavailable = bookingCourseRepository.isTrainerUnavailable(
                course.getOwner().getId(),
                bookingCourse.getCourseStartDate(),
                bookingCourse.getCourseEndDate()
        );

        if (isUnavailable) {
            return "Trainer is not available during the requested dates!";
        }

        // Step 3: Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found!"));

        // Step 4: Create and save booking
        bookingCourse.setUser(user);
        bookingCourse.setCourse(course);
        bookingCourse.setBookingDate(LocalDate.now());

        bookingCourseRepository.save(bookingCourse);

        return "Course booked successfully!";
    }
    public void updateBookingCourse(Integer bookingCourseId, BookingCourse bookingCourse) {
        // Step 1: Validate existing booking
        BookingCourse existingBooking = bookingCourseRepository.findById(bookingCourseId)
                .orElseThrow(() -> new ApiException("Booking course not found"));

        // Step 2: Validate course existence
        Course course = existingBooking.getCourse();
        if (course == null) {
            throw new ApiException("Associated course not found");
        }

        // Step 3: Check trainer availability
        boolean isUnavailable = bookingCourseRepository.isTrainerUnavailable(
                course.getOwner().getId(),
                bookingCourse.getCourseStartDate(),
                bookingCourse.getCourseEndDate()
        );

        if (isUnavailable) {
            throw new ApiException("Trainer is not available during the updated dates!");
        }

        // Step 4: Update booking details
        existingBooking.setCourseStartDate(bookingCourse.getCourseStartDate());
        existingBooking.setCourseEndDate(bookingCourse.getCourseEndDate());

        // Save updated booking
        bookingCourseRepository.save(existingBooking);
    }


    public void deleteBookingCourse(Integer bookingCourse_id) {
        BookingCourse bookingCourse1 = bookingCourseRepository.findBookingCourseById(bookingCourse_id);
        if (bookingCourse1 == null) {
            throw new ApiException("Booking course not found");
        }
        bookingCourseRepository.delete(bookingCourse1);
    }

}
