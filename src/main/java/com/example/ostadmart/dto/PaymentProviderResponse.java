package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProviderResponse {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "session_key")
    private String sessionKey;

    @JsonProperty(value = "payment_url")
    private String gatewayPageURL;

}
