package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
   private final ItemRepository itemRepository;

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
    String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
       Optional<Item> result =  itemRepository.findById(id);
       // 데이터 있는지 체크 !sEmpty()
       if(result.isPresent()) {
           model.addAttribute("item", result.get());
           return "detail.html";
       } else {
           return "redirect:/list";
       }
    }
}
