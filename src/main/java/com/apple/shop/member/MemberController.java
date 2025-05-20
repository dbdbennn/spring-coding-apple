package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    @GetMapping("/register")
    String register() {
        return "register.html";
    }

    @PostMapping("/member")
    public String saveMember(String username,
                           String password,
                           String displayName) throws Exception {


        var result = memberRepository.findByUsername(username);

        if (username.isEmpty() || displayName.isEmpty() || password.isEmpty()) {
            throw new Exception("모든 항목을 입력해주세요");
        }

        // ✅ 아이디 중복 검사
        if (result.isPresent()) {
            throw new Exception("존재하는 아이디");
        }

        // ✅ 길이 검사
        if (username.length() < 8 || password.length() < 8) {
            throw new Exception("너무 짧음");
        }



        Member member = new Member();
        member.setUsername(username);
        var hash = pwEncoder.encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        memberRepository.save(member);

        return "redirect:/list/page/1";
    }

    @GetMapping("/login")
    public String login() {
        var result = memberRepository.findByUsername("jungeun1234");
        System.out.println(result);
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            CustomUser result = (CustomUser) auth.getPrincipal();
            System.out.println(result.getDisplayName());

            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", auth.getName());
            model.addAttribute("authorities", auth.getAuthorities());

            // "일반유저" 권한이 있는지 확인
            boolean hasUserRole = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("일반유저"));
            model.addAttribute("hasUserRole", hasUserRole);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDto(result.getUsername(), result.getDisplayName());

        return data;
    }

}

// DTO(데이터 변환용 클래스)
// 재사용 용이
class MemberDto {
    public String username;
    public String displayName;

    MemberDto(String a, String b) {
        this.username = a;
        this.displayName = b;
    }
}
