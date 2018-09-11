package com.cxx.annotation.service;
import com.cxx.annotation.TestObject;
import com.cxx.annotation.reposity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository1;

//    @Autowired
//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public void add() {
        System.out.println("UserService add");
        System.out.println("userReposityp"+userRepository1);
        userRepository1.save();
    }
}
