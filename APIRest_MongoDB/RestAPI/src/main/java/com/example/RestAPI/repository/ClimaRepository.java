package com.example.RestAPI.repository;

import com.example.RestAPI.model.ClimaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimaRepository extends MongoRepository<ClimaEntity,String> {

}
