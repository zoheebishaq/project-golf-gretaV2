package com.example.projectgolfgreta.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private long id;
    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String login;
    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 128)
    private String password;
    @Basic
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;
    @Basic
    @Column(name = "MAIL", nullable = false, length = 100)
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="USER_GROUP",
            joinColumns =@JoinColumn(name = "ID_USER", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID"))
    private Set<Group> groups;


    public User() {
    }

    public User(String login, String password, String mail) {
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                active == user.active &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mail, user.mail);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, active, mail);
    }

}
