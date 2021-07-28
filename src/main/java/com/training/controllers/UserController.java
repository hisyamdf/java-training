package com.training.controllers;

 import javax.servlet.http.HttpSession;

 import com.training.data.entity.User;
 import com.training.data.repos.UserRepo;
 import com.training.dto.LoginData;
 import com.training.utility.MD5Generator;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

 @Controller
 @RequestMapping("/users")
 public class UserController {

     @Autowired
     private UserRepo repo;

     @Autowired
     private HttpSession session;

     @GetMapping("/register")
     public String add(Model model){
         model.addAttribute("user", new User());
         return "register";
     }

     @PostMapping("/register")
     public String register(User user, Model model) throws Exception {
         user.setPassword(MD5Generator.generate(user.getPassword()));
         repo.save(user);
         return "redirect:/";
     }

     @GetMapping("/login")
     public String login(Model model){
         model.addAttribute("login", new LoginData());
         return "login";
     }

     @PostMapping("/login")
     public String prosesLogin(LoginData loginData, RedirectAttributes attr) throws Exception{
         User user = repo.findByEmail(loginData.getEmail());

         if(user!=null){
             if(user.getPassword().equals(MD5Generator.generate(loginData.getPassword()))){
                 session.setAttribute("CURRENT_USER", user.getEmail());
                 return "redirect:/";
             }
             attr.addFlashAttribute("ERROR", "Email or Password incorrect");
             return "redirect:/users/login";
         }
         attr.addFlashAttribute("ERROR", "Email or Password incorrect");
         return "redirect:/users/login";
     }

     @GetMapping("/logout")
     public String prosesLogout(){
         session.invalidate();
         return "redirect:/";
     }
 }