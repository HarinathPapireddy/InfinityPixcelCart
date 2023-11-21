package io.bootify.infinity_cart.rest;

import io.bootify.infinity_cart.model.PayementDTO;
import io.bootify.infinity_cart.service.PayementService;
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
@RequestMapping(value = "/api/payements", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayementResource {

    private final PayementService payementService;

    public PayementResource(final PayementService payementService) {
        this.payementService = payementService;
    }

    @GetMapping
    public ResponseEntity<List<PayementDTO>> getAllPayements() {
        return ResponseEntity.ok(payementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayementDTO> getPayement(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(payementService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPayement(@RequestBody @Valid final PayementDTO payementDTO) {
        final Long createdId = payementService.create(payementDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePayement(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PayementDTO payementDTO) {
        payementService.update(id, payementDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePayement(@PathVariable(name = "id") final Long id) {
        payementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
