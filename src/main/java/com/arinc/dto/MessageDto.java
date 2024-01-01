package com.arinc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageDto {
    String message;
    Integer customerId;
}
