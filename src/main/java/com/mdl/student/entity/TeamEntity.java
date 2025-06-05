package com.mdl.student.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Table(name = "team")
public class TeamEntity {

    @Id
    @Column(name ="name")
    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,     // if you remove a Student from this list, JPA will delete it
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<StudentEntity> students = new ArrayList<>();

    @Column(name ="updated_at")
    private Date updatedAt;

    @Column(name ="deleted_at")
    private Date deletedAt;
}
