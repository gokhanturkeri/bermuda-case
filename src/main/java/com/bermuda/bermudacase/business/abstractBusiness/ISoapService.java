package com.bermuda.bermudacase.business.abstractBusiness;

import java.util.List;

public interface ISoapService {
    List<String> post(String wsdlUrl, String request, String requestType) throws Exception;
}