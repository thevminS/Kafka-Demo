package com.kafka.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {
    private long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
