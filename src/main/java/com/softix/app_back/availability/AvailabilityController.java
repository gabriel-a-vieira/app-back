package com.softix.app_back.availability;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    @GetMapping
    public Page<AvailabilityDTO> findAll(@RequestParam(required = false) String search,

                                         @RequestParam(required = false) String professionalId,

                                         @RequestParam(required = false) DayOfWeek dayWeek,

                                         @RequestParam(required = false) String companyId,

                                         @RequestParam(defaultValue = "0") int page,

                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("professionalId"), Sort.Order.asc("dayWeek"), Sort.Order.asc("startTime")));

        return availabilityService.findAll(search, professionalId, dayWeek, companyId, pageable);

    }

    @GetMapping("/{id}")
    public AvailabilityDTO findById(@PathVariable String id) {
        return availabilityService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityDTO create(@Valid @RequestBody AvailabilityDTO dto) {
        return availabilityService.save(dto);
    }

    @PutMapping("/{id}")
    public AvailabilityDTO update(@PathVariable String id,
                                  @Valid @RequestBody AvailabilityDTO dto) {
        return availabilityService.update(id, dto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestBody List<String> ids) {
        availabilityService.deleteMany(ids);
    }

    @GetMapping("/by-day")
    public List<AvailabilityDTO> findByDay(@RequestParam String professionalId,
                                           @RequestParam DayOfWeek day) {
        return availabilityService.getByDay(professionalId, day);
    }

}