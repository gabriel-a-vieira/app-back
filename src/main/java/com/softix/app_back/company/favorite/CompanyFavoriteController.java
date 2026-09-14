package com.softix.app_back.company.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyFavoriteController {

    private final CompanyFavoriteService companyFavoriteService;

    @PutMapping("/{id}/favorite")
    public Map<String, Boolean> toggleFavorite(@PathVariable String id) {
        return Map.of("favorited", companyFavoriteService.toggle(id));
    }

}
