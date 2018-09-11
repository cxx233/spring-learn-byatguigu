package com.cxx.annotation.reposity;
import org.springframework.stereotype.Repository;
//@Repository(value = "userRepository")
@Repository("userRepository1")
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepostity save...");
    }
}
