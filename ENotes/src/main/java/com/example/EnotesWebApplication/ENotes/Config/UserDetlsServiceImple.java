package com.example.EnotesWebApplication.ENotes.Config;
import com.example.EnotesWebApplication.ENotes.Entity.User;
import com.example.EnotesWebApplication.ENotes.Repository.HomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetlsServiceImple implements UserDetailsService {

    @Autowired
    private HomeRepo homeRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=homeRepo.findByEmail(username);
        if(user==null){
            throw  new UsernameNotFoundException("Not exits");
        }
        else{
            CustomUserDtls customUserDtls= new CustomUserDtls(user);
            return customUserDtls;
        }


    }
}
