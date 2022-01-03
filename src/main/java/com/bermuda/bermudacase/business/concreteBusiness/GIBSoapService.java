package com.bermuda.bermudacase.business.concreteBusiness;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bermuda.bermudacase.business.abstractBusiness.ISoapService;

@Service("gibSoapService")
public class GIBSoapService implements ISoapService {

    Logger logger = LoggerFactory.getLogger(GIBSoapService.class);

    @Override
    public List<String> post(String wsdlUrl, String request, String requestType) throws Exception {
        if (requestType.equals("getAppRespRequest"))
            return getAppRespRequest(wsdlUrl, request);
        return new ArrayList<>();
    }

    private List<String> getAppRespRequest(String wsdlUrl, String request) throws Exception {
        URL urlObject = new URL(wsdlUrl);
        HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
        logger.info("Connection established to GIB Soap Web Service!");
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(request);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.replace("&lt;", "<").replace("&gt;", ">"));
        }
        in.close();
        con.disconnect();

        String documentResponseTag = getElementValueByTag(response.toString(),
                                                          "cac:DocumentResponse");
        String lineResponseTag = getElementValueByTag(documentResponseTag, "cac:LineResponse");
        String responseTag = getElementValueByTag(lineResponseTag, "cac:Response");
        String responseCode = getElementValueByTag(responseTag, "cbc:ResponseCode");
        String description = getElementValueByTag(responseTag, "cbc:Description");

        List<String> responseResults = new ArrayList<>();
        responseResults.add(responseCode);
        responseResults.add(description);
        return responseResults;
    }

    /**
     * Finding element's value from XML with tag
     * @param xml XML String
     * @param tag Element's tag
     * @return
     * @throws Exception
     */
    private String getElementValueByTag(String xml, String tag) throws Exception {
        String tagStart = "<" + tag + ">";
        String tagEnd = "</" + tag + ">";
        if (xml.split(tagStart).length > 2)
            throw new Exception(
                            "There is more than one of this element. Please use more specific tag !");
        return xml.split(tagStart)[1].split(tagEnd)[0].replace("\t", "");
    }
}
