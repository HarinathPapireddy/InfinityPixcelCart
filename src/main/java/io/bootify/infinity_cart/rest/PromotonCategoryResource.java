package io.bootify.infinity_cart.rest;

import io.bootify.infinity_cart.model.PromotonCategoryDTO;
import io.bootify.infinity_cart.service.PromotonCategoryService;
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
@RequestMapping(value = "/api/promotonCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromotonCategoryResource {

    private final PromotonCategoryService promotonCategoryService;

    public PromotonCategoryResource(final PromotonCategoryService promotonCategoryService) {
        this.promotonCategoryService = promotonCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<PromotonCategoryDTO>> getAllPromotonCategories() {
        return ResponseEntity.ok(promotonCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotonCategoryDTO> getPromotonCategory(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(promotonCategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPromotonCategory(
            @RequestBody @Valid final PromotonCategoryDTO promotonCategoryDTO) {
        final Long createdId = promotonCategoryService.create(promotonCategoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePromotonCategory(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PromotonCategoryDTO promotonCategoryDTO) {
        promotonCategoryService.update(id, promotonCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePromotonCategory(@PathVariable(name = "id") final Long id) {
        promotonCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
