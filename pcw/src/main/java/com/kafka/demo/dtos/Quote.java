package com.kafka.demo.dtos;

import lombok.*;

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
