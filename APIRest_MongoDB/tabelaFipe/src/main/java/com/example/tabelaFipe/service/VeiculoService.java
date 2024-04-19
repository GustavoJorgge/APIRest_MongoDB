package com.example.tabelaFipe.service;

import com.example.tabelaFipe.model.MarcasEntity;
import com.example.tabelaFipe.model.ModeloEntity;
import com.example.tabelaFipe.repository.ModeloRepository;
import com.example.tabelaFipe.repository.VeiculoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public String consultarUrl(String endpoint) {
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(endpoint, String.class);
        if(responseEntity !=null ){
            dados = responseEntity.getBody();
        }else{
            dados = "Não foi possivel buscar as marcas";
        }
        return dados;
    }


    public String BuscaMarcas() {
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        try {
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                JSONArray marcasArray = new JSONArray(responseBody);

                for (int i = 0; i < marcasArray.length(); i++) {
                    JSONObject marcaObj = marcasArray.getJSONObject(i);
                    String codigo = marcaObj.getString("codigo");
                    String nome = marcaObj.getString("nome");

                    MarcasEntity marcasEntity = new MarcasEntity();
                    marcasEntity.setCodigo(codigo);
                    marcasEntity.setMarca(nome);

                    veiculoRepository.save(marcasEntity);
                }
                return "Marcas de veículos buscadas e salvas com sucesso.";
            } else {
                return "Falha ao buscar as marcas. Código de status: " + responseEntity.getStatusCodeValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível buscar e salvar as marcas de veículos.";
        }
    }

    public List<MarcasEntity> BuscaMarcasViaBanco(){
        return veiculoRepository.findAll();
    }
    public String ConsultaModelos(int id) {
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + id + "/modelos";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                JSONObject responseJson = new JSONObject(responseBody);

                // Verifica se o objeto JSON possui a chave "modelos"
                if (responseJson.has("modelos")) {
                    JSONArray modelosArray = responseJson.getJSONArray("modelos");

                    for (int i = 0; i < modelosArray.length(); i++) {
                        JSONObject modeloObj = modelosArray.getJSONObject(i);
                        int codigo = modeloObj.getInt("codigo");
                        String nome = modeloObj.getString("nome");

                        ModeloEntity modeloEntity = new ModeloEntity();
                        modeloEntity.setCodigo(String.valueOf(codigo));
                        modeloEntity.setModelo(nome);
                        modeloEntity.setID_marca(id);

                        modeloRepository.save(modeloEntity);
                    }

                    return "Consulta de modelos concluída com sucesso.";
                } else {
                    return "O objeto JSON retornado não possui a chave 'modelos'.";
                }
            } else {
                return "A API retornou um código de status não bem-sucedido: " + responseEntity.getStatusCodeValue();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Exceções específicas para erros HTTP 4xx e 5xx
            return "Erro de cliente ou servidor ao consultar os modelos: " + e.getMessage();
        } catch (Exception e) {
            // Captura de outras exceções genéricas
            e.printStackTrace();
            return "Ocorreu um erro ao consultar os modelos: " + e.getMessage();
        }
    }

    public ModeloEntity atualizar(String id, ModeloEntity newModelo) {
        ModeloEntity existingModelo = modeloRepository.findById(id).orElse(null);

        if(existingModelo != null){
            existingModelo.setModelo(newModelo.getModelo());
            existingModelo.setID_marca(newModelo.getID_marca());
            return modeloRepository.save(existingModelo);
        }else{
            return null;
        }
    }

    public String consultaAno(int marca, int modelo) {
        return consultarUrl("https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" +  modelo+ "/anos");
    }

    public String consultaValor(int marca, int modelo, String ano){
        return consultarUrl("https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" +  modelo + "/anos/" + ano);
    }
}
