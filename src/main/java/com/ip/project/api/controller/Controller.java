package com.ip.project.api.controller;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.ip.project.api.constants.Constants;
import com.ip.project.api.utils.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    private AWSSimpleSystemsManagement ssmManager;

    @GetMapping(Constants.GET_PARAMETER_RESOURCE)
    @ResponseBody
    public Map<String, String> getParameter(@PathVariable String parameter) throws Exception {
        Map<String, String> parametersMap = new HashMap<>();

        String value = ssmManager.getParameter(new GetParameterRequest().withName(parameter).withWithDecryption(true)).getParameter().getValue();

        parametersMap.put(parameter, value);

        return parametersMap;
    }
}
