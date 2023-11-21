package io.bootify.infinity_cart.rest;

import io.bootify.infinity_cart.model.WishlistCartDTO;
import io.bootify.infinity_cart.service.WishlistCartService;
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
@RequestMapping(value = "/api/wishlistCarts", produces = MediaType.APPLICATION_JSON_VALUE)
public class WishlistCartResource {

    private final WishlistCartService wishlistCartService;

    public WishlistCartResource(final WishlistCartService wishlistCartService) {
        this.wishlistCartService = wishlistCartService;
    }

    @GetMapping
    public ResponseEntity<List<WishlistCartDTO>> getAllWishlistCarts() {
        return ResponseEntity.ok(wishlistCartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistCartDTO> getWishlistCart(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(wishlistCartService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteWishlistCart(@PathVariable(name = "id") final Long id) {
        wishlistCartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
