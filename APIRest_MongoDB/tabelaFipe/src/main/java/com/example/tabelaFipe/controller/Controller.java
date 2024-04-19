package com.example.tabelaFipe.controller;

import com.example.tabelaFipe.model.MarcasEntity;
import com.example.tabelaFipe.model.ModeloEntity;
import com.example.tabelaFipe.repository.VeiculoRepository;
import com.example.tabelaFipe.service.VeiculoService;
import org.apache.catalina.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class Controller {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/marcas")
    public String BuscaMarcas(){
        return veiculoService.BuscaMarcas();
    }

    @GetMapping("/todasMarcas")
    public List<MarcasEntity> BuscaMarcasViaBanco(){
        return veiculoService.BuscaMarcasViaBanco();
    }

    @GetMapping("/modelos/{marca}")
    public String ConsultaModelos(@PathVariable int marca){
        return veiculoService.ConsultaModelos(marca);
    }

    @GetMapping("anos/{marca}/{modelo}")
    public String ConsultaAnos(@PathVariable int marca, @PathVariable int modelo){
        return veiculoService.consultaAno(marca,modelo);
    }

    @GetMapping("valor/{marca}/{modelo}/{ano}")
    public String ConsultaValor(@PathVariable int marca, @PathVariable int modelo, @PathVariable String ano){
        return veiculoService.consultaValor(marca, modelo, ano);
    }

    @PutMapping("/{id}")
    public ModeloEntity atualizar(@PathVariable String id, @RequestBody ModeloEntity newModelo){
        return veiculoService.atualizar(id,newModelo);
    }
}
