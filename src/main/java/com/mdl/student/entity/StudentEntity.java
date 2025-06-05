package com.mdl.student.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Table(name = "student")
public class StudentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name ="name")
    private String name;

    @Column(name ="email")
    private String email;

    @Column(name ="birthdate")
    private Date birthdate;

   @Column(name ="created_at")
    private Date createAt;

    @Column(name ="updated_at")
    private Date updatedAt;

    @Column(name ="deleted_at")
    private Date deletedAt;


    /**
     * JoinColumn(name="team_name") → the column in STUDENT table
     * referencedColumnName="name" → points to TEAM(name), which is PK here.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "team",
            referencedColumnName = "name",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_student_team_name")
    )
    @JsonBackReference
    private TeamEntity team;
}
