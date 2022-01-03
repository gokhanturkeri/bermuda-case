package com.bermuda.bermudacase.business.concreteBusiness;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bermuda.bermudacase.business.abstractBusiness.IBermudaService;

@Service
public class BermudaService implements IBermudaService {

    Logger logger = LoggerFactory.getLogger(BermudaService.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public BermudaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void saveGuids(List<String> guids) {
        logger.info("RestAPI triggered for saving guids.");
        logger.info("Guids are queued...");
        for (String guid : guids)
            rabbitTemplate.convertAndSend("guidQueue", guid);
    }
}