package com.bermuda.bermudacase.restApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bermuda.bermudacase.business.abstractBusiness.IBermudaService;
import com.bermuda.bermudacase.business.concreteBusiness.BermudaService;

@RestController
@RequestMapping("/bermuda")
public class BermudaController {

    private IBermudaService bermudaService;

    @Autowired
    public BermudaController(BermudaService bermudaService) {
        this.bermudaService = bermudaService;
    }

    @PostMapping(value = "/saveGuids")
    public void saveGuids(@RequestBody List<String> guids) {
        bermudaService.saveGuids(guids);
    }

}