package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void editItem(Item item, String title, Integer price) throws Exception {
        if(title.length() >= 100) {
            throw new Exception("제목은 100자 미만이어야 합니다.");
        }
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
}
