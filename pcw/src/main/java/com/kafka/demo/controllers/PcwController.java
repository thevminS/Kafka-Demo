package com.kafka.demo.controllers;

import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.Quote;
import com.kafka.demo.services.PcwService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PcwController {
    private final PcwService pcwService;

    @PostMapping("/prices")
    public Price getPrices(@RequestBody Quote quote) throws ExecutionException, InterruptedException {
        Price price = pcwService.sendToPrice(quote);
        return price;
    }
}
