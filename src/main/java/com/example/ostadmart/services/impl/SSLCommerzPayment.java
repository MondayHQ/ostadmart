package com.example.ostadmart.services.impl;

import java.util.Map;

import com.example.ostadmart.models.Payment;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Value;

// Local Imports
import com.example.ostadmart.services.PaymentProvider;
import com.example.ostadmart.dto.PaymentProviderResponse;

@Primary
@Component
public class SSLCommerzPayment implements PaymentProvider {

    @Value("${ssl_commerz.sandbox.url}")
    private String url;

    @Value("${ssl_commerz.sandbox.store_id}")
    private String storeId;

    @Value("${ssl_commerz.sandbox.store_passwd}")
    private String storePassword;

    @Value("${payment.url.on_success}")
    private String urlOnSuccess;

    @Value("${payment.url.on_failure}")
    private String urlOnFailure;

    @Value("${payment.url.on_cancel}")
    private String urlOnCancel;

    @Value("${payment.url.ipn}")
    private String urlIPN;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PaymentProviderResponse initiatePayment(Payment payment) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("store_id", storeId);
        map.add("store_passwd", storePassword);
        map.add("total_amount", payment.getAmount().toString());
        map.add("currency", "BDT");
        map.add("tran_id", payment.getTransactionId()); // For Idempotency
        map.add("success_url", urlOnSuccess);
        map.add("fail_url", urlOnFailure);
        map.add("cancel_url", urlOnCancel);
        map.add("ipn_url", urlIPN);

        // SSL Commerz returns a JSON with GatewayPageURL
        var response = restTemplate.postForObject(url, map, Map.class);
        assert response != null;

        return PaymentProviderResponse.builder()
                .status(response.get("status").toString())
                .sessionKey(response.get("sessionkey").toString())
                .gatewayPageURL(response.get("GatewayPageURL").toString())
                .build();
    }

    @Override
    public boolean validatePayment(Map<String, String> requestParams) {
        String valId = requestParams.get("val_id");

        // SSL Commerz Validation URL
        String url = String.format(
                "https://sandbox.sslcommerz.com/validator/api/validationserverAPI.php?val_id=%s&store_id=%s&store_passwd=%s&format=json",
                valId, storeId, storePassword
        );

        Map response = restTemplate.getForObject(url, Map.class);

        assert response != null;

        // Verify status and amount match your database
        return "VALID".equals(response.get("status")) || "VALIDATED".equals(response.get("status"));
    }
}
