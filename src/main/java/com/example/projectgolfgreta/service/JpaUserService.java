package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.dao.securite.GroupRepository;
import com.example.projectgolfgreta.dao.securite.UserRepository;
import com.example.projectgolfgreta.domain.Group;
import com.example.projectgolfgreta.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JpaUserService {
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JpaUserService(UserRepository userRepository,
                          GroupRepository groupRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setGroups(user.getGroups());
        userRepository.save(user);
    }

    public void addUser(String name ,String email, String mdp,Long idGroup){
        Group group = this.groupRepository.findById(idGroup).get();
        User user = new User(name,mdp,email);
        Set<Group> groups = new HashSet<Group>();
        System.out.println(group.getRole());
        user.setGroups(groups);
        groups.add(group);
        this.save(user);
    }

    public User findByUserName(String userName){
        return userRepository.findByLogin(userName);
    }

}
