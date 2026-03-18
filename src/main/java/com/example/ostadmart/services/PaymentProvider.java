package com.example.ostadmart.services;

import java.util.Map;

// Local Imports
import com.example.ostadmart.models.Payment;
import com.example.ostadmart.dto.PaymentProviderResponse;

public interface PaymentProvider {

    PaymentProviderResponse initiatePayment(Payment payment);

    boolean validatePayment(Map<String, String> requestParams);

}
