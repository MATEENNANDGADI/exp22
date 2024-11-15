package com.example.exp22.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.exp22.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {
}
