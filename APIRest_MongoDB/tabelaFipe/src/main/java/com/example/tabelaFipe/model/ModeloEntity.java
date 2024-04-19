package com.example.tabelaFipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "modelos")
public class ModeloEntity {

    @Id
    private String codigo;
    private String modelo;
    private int ID_marca;

    public Integer getID_marca() {
        return ID_marca;
    }

    public void setID_marca(Integer ID_marca) {
        this.ID_marca = ID_marca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
