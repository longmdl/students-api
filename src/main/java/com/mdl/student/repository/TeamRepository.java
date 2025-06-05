package com.mdl.student.repository;

import com.mdl.student.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, String> {

    @Query("select t from TeamEntity t where :name is null or t.name like lower(concat('%', :name, '%'))")
    List<TeamEntity> findAllbyName(String name);

}
