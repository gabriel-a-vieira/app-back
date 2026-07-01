package com.softix.app_back.client;

import com.softix.app_back.payment.PaymentMethod;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public Page<ClientResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String preferredPaymentMethod,
            @RequestParam(required = false) String companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return clientService.findAll(
                search,
                name,
                cpfCnpj,
                phone,
                city,
                state,
                status,
                preferredPaymentMethod,
                companyId,
                pageable
        );
    }

    @GetMapping("/{id}")
    public ClientResponse findById(@PathVariable String id) {
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse save(@Valid @RequestBody ClientRequest request) {
        return clientService.save(request);
    }

    @PutMapping("/{id}")
    public ClientResponse update(
            @PathVariable String id,
            @Valid @RequestBody ClientRequest request
    ) {
        return clientService.update(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestBody List<String> ids) {
        clientService.deleteMany(ids);
    }

    @GetMapping("/payment-methods")
    public List<String> findPaymentMethods() {
        return Arrays.stream(PaymentMethod.values())
                .map(Enum::name)
                .toList();
    }
}