package com.cw.springsecurityjwt.services;

//import com.cw.admin.exceptions.APIRequestException;
//import com.cw.admin.models.washPack;
//import com.cw.admin.repositories.washPackRepository;
import com.cw.springsecurityjwt.exceptionhandler.APIRequestException;
import com.cw.springsecurityjwt.models.washPack;
import com.cw.springsecurityjwt.repositories.washPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class washPackService {
    @Autowired
    private washPackRepository wr;
    public washPack addWp(washPack pack){
        return wr.save(pack);
    }
    public List<washPack> findAllWp(){
        return wr.findAll();
    }
    //d
    public ResponseEntity<washPack> findById(String id){
         washPack pack = wr.findById(id).orElseThrow(()->new APIRequestException("Wash pack with "+id+" not found"));
        return ResponseEntity.ok(pack);
    }
//    public Integer getCostByName(String name){
//        int cost = wr.findByName(name);
//        return cost;
//    }
    public washPack findByName(String name){
        return wr.findAll().stream().filter(x->x.getName().contains(name)).findFirst().get();
    }
    //d
    public ResponseEntity<Map<String,Boolean>> deleteWp(String id){
        washPack pack = wr.findById(id).orElseThrow(()->new APIRequestException("wash pack with "+id+" not found"));
        wr.delete(pack);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
    public ResponseEntity<washPack> updateWp(String id, washPack pack){
        washPack existingWp = wr.findById(id).orElseThrow(()->new APIRequestException("wash pack with "+id+" not found"));
        existingWp.setName(pack.getName());
        existingWp.setCost(pack.getCost());
        existingWp.setDescription(pack.getDescription());
        existingWp.setImage(pack.getImage());
        washPack response = wr.save(existingWp);
        return ResponseEntity.ok(response);

    }



}
