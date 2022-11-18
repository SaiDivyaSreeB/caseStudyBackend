package com.cw.springsecurityjwt.repositories;

//import com.cw.admin.models.washPack;
//import org.springframework.data.mongodb.repository.MongoRepository;

import com.cw.springsecurityjwt.models.washPack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface washPackRepository extends MongoRepository<washPack, String> {
//int findByName (String washpack);
}
