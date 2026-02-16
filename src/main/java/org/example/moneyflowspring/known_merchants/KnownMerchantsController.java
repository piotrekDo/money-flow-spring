package org.example.moneyflowspring.known_merchants;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class KnownMerchantsController {

    private final KnownMerchantsService knownMerchantsService;

    @GetMapping("/all")
    List<KnownMerchantDto> findAllKnownMerchants() {
        return knownMerchantsService.findAllKnownMerchants();
    }

    @PostMapping("/add-subcategory")
    KnownMerchantDto addSubcategory(
            @RequestParam Long merchantId,
            @RequestParam Long subcategoryId) {
        return knownMerchantsService.addSubcategoryToMerchant(merchantId, subcategoryId);
    }
}
