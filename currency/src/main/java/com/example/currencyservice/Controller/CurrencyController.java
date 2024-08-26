package com.example.currencyservice.Controller;

import com.example.currencyservice.dto.CurrencyConversionRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    @ResponseBody
    Double
    changeCurrency(@RequestParam("amount") double amount,
                   @RequestParam("currentCurrency") String currentCurrency,
                   @RequestParam("newCurrency") String newCurrency) {
        CurrencyConversionRequest request = new CurrencyConversionRequest(amount, currentCurrency, newCurrency);
        return request.getAmount();
    }
}
