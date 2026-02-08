package org.example.moneyflowspring.known_merchants;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class KnownMerchantsController {

    private final KnownMerchantsService knownMerchantsService;

    @GetMapping("/all")
    List<KnownMerchantDto> findAllCategories() {
        return knownMerchantsService.findAllKnownMerchants();
    }
}
