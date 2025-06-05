package com.mdl.student.repository;

import com.mdl.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findByName(String name);

    @Query("SELECT s FROM StudentEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<StudentEntity> findStudentBySearchText(@Param("searchText") String searchText);

    List<StudentEntity> findByDeletedAtIsNull();
}