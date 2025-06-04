package com.mdl.student.Repositories;

import com.mdl.student.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends JpaRepository<Student, Integer> {
    List<Student> findByName(String name);
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Student> findStudentBySearchText(@Param("searchText") String searchText);

    List<Student> findByDeletedAtIsNull();
}