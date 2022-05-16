package com.macky.todo.v2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private Long id;

    private String first_name;
    private String last_name;

    @Temporal(TemporalType.DATE)
    private Date birth_date;

    @Column(unique = true, nullable=false)
    private String email;
    private String password;

    @Setter(AccessLevel.NONE)
    private Boolean active = true;

}
