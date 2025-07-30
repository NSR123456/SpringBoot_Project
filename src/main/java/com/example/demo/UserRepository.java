package com.example.demo;



import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // CRUD methods
    boolean existsByStudentId(String studentId);

    User findByStudentId(String studentId);
}

