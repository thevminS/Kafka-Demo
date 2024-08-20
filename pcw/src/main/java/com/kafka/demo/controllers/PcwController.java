package com.kafka.demo.controllers;

import com.kafka.demo.dtos.Quote;
import com.kafka.demo.services.PcwService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PcwController {
    private final PcwService pcwService;

    @PostMapping("/prices")
    public CompletableFuture<?> getPrices(@RequestBody Quote quote){
        return pcwService.sendToPrice(quote);
    }
}
