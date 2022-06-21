package com.nttdata.bc19.msmanagementfixedtermaccount.request;

import lombok.Data;

@Data
public class FixedTermAccountPersonRequest {
    private double amount;
    private String accountNumber;
    private String idPersonClient;
    private String idPasiveProduct;
}
