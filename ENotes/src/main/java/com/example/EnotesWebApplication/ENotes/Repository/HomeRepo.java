package com.example.EnotesWebApplication.ENotes.Repository;

import com.example.EnotesWebApplication.ENotes.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepo extends JpaRepository<User,Integer>{
    public User findByEmail(String email);
}
