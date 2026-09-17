package com.softix.app_back.professional;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.permission.CrudAction;
import com.softix.app_back.permission.RequiresPermission;
import com.softix.app_back.permission.SystemModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/professional")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @RequiresPermission(module = SystemModule.PROFESSIONAL, action = CrudAction.LIST)
    @GetMapping
    public Page<ProfessionalResponse> findAll(@RequestParam(required = false) String search,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String cpfCnpj,
                                              @RequestParam(required = false) String phone,
                                              @RequestParam(required = false) String city,
                                              @RequestParam(required = false) String state,
                                              @RequestParam(required = false) String status,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return professionalService.findAll(search, name, cpfCnpj, phone, city, state, status, pageable);

    }

    @RequiresPermission(module = SystemModule.PROFESSIONAL, action = CrudAction.LIST)
    @GetMapping("{id}")
    public ResponseEntity<ProfessionalResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(professionalService.findById(id));
    }

    @RequiresPermission(module = SystemModule.PROFESSIONAL, action = CrudAction.CREATE)
    @PostMapping
    public ResponseEntity<ProfessionalResponse> save(@Valid @RequestBody ProfessionalDTO dto) {

        Professional professional = professionalService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfessionalResponse.fromEntity(professional));

    }

    @RequiresPermission(module = SystemModule.PROFESSIONAL, action = CrudAction.UPDATE)
    @PutMapping("{id}")
    public ResponseEntity<ProfessionalResponse> update(@PathVariable String id,
                                                       @Valid @RequestBody ProfessionalDTO dto) {

        Professional professional = professionalService.update(id, dto);
        return ResponseEntity.ok(ProfessionalResponse.fromEntity(professional));

    }

    @RequiresPermission(module = SystemModule.PROFESSIONAL, action = CrudAction.DELETE)
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestBody List<String> ids) {

        professionalService.deleteMany(ids);
        return ResponseEntity.noContent().build();

    }

}