package com.example.tabelaFipe.repository;

import com.example.tabelaFipe.model.MarcasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends MongoRepository<MarcasEntity, String> {
}
