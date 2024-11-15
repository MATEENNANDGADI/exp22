package com.example.exp22.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.exp22.Repository.ItemRepository;
import com.example.exp22.model.Item;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        if (item.getName() == null || item.getDiscription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(item);
        }
        Item createdItem = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        return itemRepository.findById(id)
                .map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item itemDetails) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setId(itemDetails.getId());
                    item.setName(itemDetails.getName());
                    item.setDiscription(itemDetails.getDiscription());
                    Item updatedItem = itemRepository.save(item);
                    return ResponseEntity.ok(updatedItem);

                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteitem(@PathVariable String id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
