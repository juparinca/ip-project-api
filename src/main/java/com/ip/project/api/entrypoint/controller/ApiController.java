package com.ip.project.api.entrypoint.controller;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.ip.project.api.entrypoint.dto.request.KeyDTO;
import com.ip.project.api.entrypoint.dto.response.KeyResponse;
import com.ip.project.api.singleton.KeySingleton;
import com.ip.project.api.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ip-api")
public class ApiController {

    @Autowired
    private AWSSimpleSystemsManagement ssmManager;

    @Value("${parameter-api.key}")
    private String nameKey;

    private HttpStatus httpStatus;
    private String description;
    private String error;
    private static final Logger logger = LogManager.getLogger(ApiController.class);

    KeySingleton keySingleton = KeySingleton.INSTANCE.getInstance();

    @PostConstruct
    public void getParameterAws() throws Exception {
        Map<String, String> parametersMap = new HashMap<>();

        String value = ssmManager.getParameter(new GetParameterRequest().withName(nameKey).withWithDecryption(true)).getParameter().getValue();

        parametersMap.put(nameKey, value);
        KeySingleton.INSTANCE.setNameKey(nameKey);
        KeySingleton.INSTANCE.setValueKey(value);
    }

    @PostMapping("/validate-key")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getKeyResponse(@RequestBody KeyDTO request) throws Exception {
        try {
            logger.info(request.toString());
            int existKey = 0;
            if(request.getNameKey().equals(keySingleton.getNameKey()) && request.getValueKey().equals(keySingleton.getValueKey())){
                existKey = 1;
                description = "El nombre y valor de la llave coinciden";
                error = "";
                httpStatus = HttpStatus.OK;
            }else{
                error = "El nombre y valor de la llave no coinciden";
                description = "";
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            KeyResponse keyResponse = new KeyResponse(existKey,"",description,error,DateUtils.getTimeStamp());

            return new ResponseEntity<>(keyResponse,httpStatus);
        } catch (Exception e) {
            logger.error(e.getCause());
            throw e;
        }
    }
}
