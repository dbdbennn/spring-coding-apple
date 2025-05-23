package com.apple.shop.comment;

import com.apple.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(
            @RequestParam String content,
            @RequestParam Long parent,
            Authentication auth
    ) {
        CustomUser result = (CustomUser) auth.getPrincipal();
        var data = new Comment();
        data.setContent(content);
        data.setUsername(result.getUsername());
        data.setParentId(parent);
        commentRepository.save(data);
        return "redirect:/detail/" + parent;
    }
    //git test
}
