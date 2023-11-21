package io.bootify.infinity_cart.AppController;


import io.bootify.infinity_cart.model.ProductDTO;
import io.bootify.infinity_cart.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/public")
public class PublicApi {

    @Autowired
    private ProductService productService;
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/getProductsByName")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam(name = "productName") final String productName) {
        return ResponseEntity.ok(productService.getProductsByName(productName));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/getProductImages")
    public ResponseEntity<List<String>> getProductImages(@RequestParam(name = "productId")final Long productId){
        return ResponseEntity.ok(productService.getImagesOfProduct(productId));
    }

}
