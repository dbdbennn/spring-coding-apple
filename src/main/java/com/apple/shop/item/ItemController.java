package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
   private final ItemRepository itemRepository;
   private final ItemService itemService;

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
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
//        throw new Exception();
       Optional<Item> result =  itemRepository.findById(id);
       // 데이터 있는지 체크 !sEmpty()
       if(result.isPresent()) {
           model.addAttribute("item", result.get());
           return "detail.html";
       } else {
           return "redirect:/list";
       }
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        }

        return "redirect:/list";
    }

    @PostMapping("/edit/{id}")
    String changeItem(@PathVariable Long id, String title, Integer price) throws Exception {
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
          Item item = result.get();
          itemService.editItem(item, title, price);
        }
        return "redirect:/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok("deleted");
    }



}
