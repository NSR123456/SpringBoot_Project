package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    // Show registration form
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        logger.info("Processing registration for user: " + user);
        
        if (result.hasErrors()) {
            logger.warning("Validation errors during registration: " + result.getAllErrors());
            return "register";
        }

        try {
            // Check if student_id already exists before saving
            if (userRepository.existsByStudentId(user.getStudentId())) {
                result.rejectValue("studentId", "duplicate.user.studentId", 
                    "Student ID " + user.getStudentId() + " already exists. Please use a different Student ID.");
                logger.warning("Attempted to register with duplicate student_id: " + user.getStudentId());
                return "register";
            }

            userRepository.save(user);
            logger.info("User successfully saved to database with ID: " + user.getId());
            return "redirect:/success";
            
        } catch (DataIntegrityViolationException e) {
            logger.severe("Data integrity violation during user registration: " + e.getMessage());
            
            // Handle the case where the constraint violation still occurs
            if (e.getMessage().contains("student_id")) {
                result.rejectValue("studentId", "duplicate.user.studentId", 
                    "Student ID already exists. Please use a different Student ID.");
            } else {
                result.reject("database.error", "A database error occurred. Please try again.");
            }
            return "register";
        } catch (Exception e) {
            logger.severe("Unexpected error during user registration: " + e.getMessage());
            result.reject("general.error", "An unexpected error occurred. Please try again.");
            return "register";
        }
    }

    // Show success page after registration
    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }

    // Show all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user_list";
    }

    // View user details
    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Invalid user ID: " + id)));
        return "user_view";
    }

    // Show edit form
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Invalid user ID: " + id)));
        return "edit_user";
    }

    // Handle edit form submission
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user, 
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warning("Validation errors during user update: " + result.getAllErrors());
            return "edit_user";
        }

        try {
            // Check if student_id already exists for a different user
            User existingUserWithSameStudentId = userRepository.findByStudentId(user.getStudentId());
            if (existingUserWithSameStudentId != null && !existingUserWithSameStudentId.getId().equals(id)) {
                result.rejectValue("studentId", "duplicate.user.studentId", 
                    "Student ID " + user.getStudentId() + " already exists for another user.");
                logger.warning("Attempted to update with duplicate student_id: " + user.getStudentId());
                return "edit_user";
            }

            user.setId(id);
            userRepository.save(user);
            logger.info("User with ID " + id + " successfully updated");
            return "redirect:/users";
            
        } catch (DataIntegrityViolationException e) {
            logger.severe("Data integrity violation during user update: " + e.getMessage());
            
            if (e.getMessage().contains("student_id")) {
                result.rejectValue("studentId", "duplicate.user.studentId", 
                    "Student ID already exists. Please use a different Student ID.");
            } else {
                result.reject("database.error", "A database error occurred. Please try again.");
            }
            return "edit_user";
        } catch (Exception e) {
            logger.severe("Unexpected error during user update: " + e.getMessage());
            result.reject("general.error", "An unexpected error occurred. Please try again.");
            return "edit_user";
        }
    }
}