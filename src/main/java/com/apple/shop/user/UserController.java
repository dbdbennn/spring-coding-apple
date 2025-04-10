package com.apple.shop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    String user(Model model) {
        List<User> user = userRepository.findAll();
        var object = new User();
        object.addOneAge();
        System.out.println("addOneAge => " + object.getAge());
        object.setAge(20);
        System.out.println("object.setAge(20) => " + object.getAge());
        object.setAge(-100);
        System.out.println("object.setAge(-100) => " + object.getAge());
        object.setAge(200);
        System.out.println("object.setAge(-200) => " + object.getAge());
        return "noticeBoard.html";
    }
}
