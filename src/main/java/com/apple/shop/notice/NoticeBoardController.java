package com.apple.shop.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NoticeBoardController {
    private final NoticeBoardRepository noticeBoardRepository;

    @GetMapping("/notice-board")
    String NoticeBoard(Model model) {
        List<NoticeBoard> noticeBoard = noticeBoardRepository.findAll();
        System.out.println(noticeBoard.get(0));
        model.addAttribute("boards", noticeBoard);

        return "noticeBoard.html";
    }

}
