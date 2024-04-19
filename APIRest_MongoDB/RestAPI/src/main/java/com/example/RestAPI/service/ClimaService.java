package com.example.RestAPI.service;
import com.example.RestAPI.model.ClimaEntity;
import com.example.RestAPI.repository.ClimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClimaService {

    @Autowired
    private ClimaRepository climaRepository;

    public String preverTempo(){
        String dadosMeteorologicos = "";
        String apiUrl = "https://apiadvisor.climatempo.com.br/api/v1/weather/locale/6879/current?token=37b9058bdffa93f6370bd3e31b991ce8";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            JSONObject jsonObject = new JSONObject(responseBody);
            String name = jsonObject.getString("name");
            String state = jsonObject.getString("state");
            JSONObject data = jsonObject.getJSONObject("data");
            double temperature = data.getDouble("temperature");
            int humidity = data.getInt("humidity");
            String condition = data.getString("condition");

            ClimaEntity climaEntity = new ClimaEntity();
            climaEntity.setName(name);
            climaEntity.setState(state);
            climaEntity.setTemperature(temperature);
            climaEntity.setHumidity(humidity);
            climaEntity.setCondition(condition);


            climaRepository.save(climaEntity);

        } else {
            dadosMeteorologicos = "Falha ao obter dados meteorologicos. Codigo de status: " + responseEntity.getStatusCode();
        }
        return dadosMeteorologicos;
    }

    public ClimaEntity inserir(ClimaEntity dadosMetereologia){

        return climaRepository.save(dadosMetereologia);
    }
}