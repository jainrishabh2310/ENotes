package com.example.EnotesWebApplication.ENotes.Controller;

import ch.qos.logback.core.model.Model;
import com.example.EnotesWebApplication.ENotes.Entity.User;
import com.example.EnotesWebApplication.ENotes.Repository.HomeRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    HomeRepo homeRepo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(HttpSession session){

        session.removeAttribute("msg");
        return "signup";
    }

    @PostMapping("/saveUser")
    public String save(@ModelAttribute User user, Model m, HttpSession session){
        user.setPass(encoder.encode(user.getPass()));
        user.setRole("ROLE_USER");


       User user1= homeRepo.save(user);
       if(user1!=null){
           session.setAttribute("msg","Register Successfully");
       }
       else{
           session.setAttribute("msg","Something went wrong");


       }

        System.out.println(user);
        return "redirect:/signup";
    }





}


