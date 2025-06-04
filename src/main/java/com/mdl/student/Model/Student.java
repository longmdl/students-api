package com.mdl.student.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Student {
    @Id
@Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name ="team")
    private String team;

    @Column(name ="birthday")
    private Date birthday;

   @Column(name ="created_at")
    private Date createAt;

    @Column(name ="updated_at")
    private Date updatedAt;

    @Column(name ="deleted_at")
    private Date deletedAt;

}
