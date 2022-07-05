package com.bc.finance.common.model.controller.finance;

import lombok.Data;

@Data
public class PaymentBatchFallback {

    private String batchNumber;

    private String reason;
}
