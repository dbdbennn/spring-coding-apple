package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ItemController {
   private final ItemRepository itemRepository;
   private final ItemService itemService;
   private final CommentRepository commentRepository;

    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    String addItem(String title, Integer price) {
        itemService.saveItem(title, price);
        return "redirect:/list/page/1";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        List<Comment> comments = commentRepository.findAllByParentId(id); // 댓글 가져오기
        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            model.addAttribute("comments", comments); // 댓글 리스트 모델에 추가
            return "detail.html";
        } else {
            return "redirect:/list/page/1";
        }
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        }

        return "redirect:/list/page/1";
    }

    @PostMapping("/edit/{id}")
    String changeItem(@PathVariable Long id, String title, Integer price) throws Exception {
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
          Item item = result.get();
          itemService.editItem(item, title, price);
        }
        return "redirect:/list/page/1";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/list/page/{pn}")
    String getListPagination(Model model, @PathVariable Integer pn) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(pn - 1, 5));
        int totalPage = result.getTotalPages();

        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage); // 숫자만 넘김
        model.addAttribute("currentPage", pn);

        return "list.html";
    }



}
