package com.example.tabelaFipe.repository;

import com.example.tabelaFipe.model.ModeloEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends MongoRepository<ModeloEntity, String> {
}
