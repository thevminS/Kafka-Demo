package com.kafka.demo.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteEvent {
    private Quote quote;
    private String correlationalId;
}
