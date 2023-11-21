package io.bootify.infinity_cart.rest;

import io.bootify.infinity_cart.model.PromotionsDTO;
import io.bootify.infinity_cart.service.PromotionsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/promotionss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromotionsResource {

    private final PromotionsService promotionsService;

    public PromotionsResource(final PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }

    @GetMapping
    public ResponseEntity<List<PromotionsDTO>> getAllPromotionss() {
        return ResponseEntity.ok(promotionsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionsDTO> getPromotions(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(promotionsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPromotions(
            @RequestBody @Valid final PromotionsDTO promotionsDTO) {
        final Long createdId = promotionsService.create(promotionsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePromotions(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PromotionsDTO promotionsDTO) {
        promotionsService.update(id, promotionsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePromotions(@PathVariable(name = "id") final Long id) {
        promotionsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
