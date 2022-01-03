package com.bermuda.bermudacase.business.concreteBusiness;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bermuda.bermudacase.business.abstractBusiness.ISoapService;
import com.bermuda.bermudacase.dataAccess.ICheckedGuidRepository;
import com.bermuda.bermudacase.entities.CheckedGuid;

@RabbitListener(queues = "guidQueue")
public class RabbitMQListener {

    Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    @Qualifier("gibSoapService")
    private ISoapService gibSoapService;

    @Autowired
    private ICheckedGuidRepository checkedGuidRepository;

    @RabbitHandler
    public void listen(String guid) {
        String request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:efat=\"http://gib.gov.tr/vedop3/eFatura\">\r\n"
                         + "   <soap:Header/>\r\n" + "   <soap:Body>\r\n"
                         + "      <efat:getAppRespRequest>\r\n" + "         <instanceIdentifier>"
                         + guid + "</instanceIdentifier>\r\n"
                         + "      </efat:getAppRespRequest>\r\n" + "   </soap:Body>\r\n"
                         + "</soap:Envelope>";
        String wsdlUrl = "https://merkeztest.efatura.gov.tr/EFaturaMerkez/services/EFatura?wsdl";

        List<String> response = null;
        try {
            logger.info("Sending a guid in the queue to the gib web service for checking...");
            response = gibSoapService.post(wsdlUrl, request, "getAppRespRequest");

            logger.info(String
                            .format("Guid is checked! GUID: %s | Response Code: %s | Description: %s",
                                    guid, response.get(0), response.get(1)));
            CheckedGuid checkedGuid = new CheckedGuid(guid, response.get(0), response.get(1));

            logger.info(String
                            .format("Checked guid saving to checkedGuids collection on MongoDB. Info: %s",
                                    checkedGuid));
            checkedGuidRepository.save(checkedGuid);

            logger.info("Checked guid is saved !");
        } catch (Exception e) {
            logger.error(String
                            .format("Error occured when listening queue ! | Error Message: %s | Request: %s",
                                    e.getMessage(), request));
        }

    }
}