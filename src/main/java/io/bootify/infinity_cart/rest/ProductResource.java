package io.bootify.infinity_cart.rest;

import io.bootify.infinity_cart.model.ProductDTO;
import io.bootify.infinity_cart.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {

    private final ProductService productService;

    public ProductResource(final ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProduct(@RequestBody @Valid final ProductDTO productDTO) {
        final Long createdId = productService.create(productDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(productService.uploadImage(file));
    }

    @PostMapping("/uploadClickBaitImage")
    public ResponseEntity<Void> uploadClickBaitImage(@RequestParam("id") final Long productId,@RequestParam("file") MultipartFile file){
        productService.uploadClickBaitImage(productId,file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/uploadMultipleImages")
    public ResponseEntity<String> uploadMultipleImages(@RequestParam("productId") final Long productId,@RequestParam("files") List<MultipartFile> files){
        productService.addImagesToProduct(productId,files);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
