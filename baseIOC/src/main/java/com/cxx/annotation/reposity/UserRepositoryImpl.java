package com.cxx.annotation.reposity;
import org.springframework.stereotype.Repository;
@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepostity save...");
    }
}
