package com.example.projectgolfgreta.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name= "GROUPS")
public class Group {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private long id;
    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "ROLE", nullable = false, length = 50)
    private String role;
    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

}
