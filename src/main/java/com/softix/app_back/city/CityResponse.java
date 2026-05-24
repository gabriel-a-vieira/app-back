package com.softix.app_back.city;

public record CityResponse(
        String id,
        String name,
        String ibgeCode,
        String tomCode,
        String state
) {

    public static CityResponse fromEntity(City city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getIbgeCode(),
                city.getTomCode(),
                city.getState() != null ? city.getState().getAbbreviation() : null
        );
    }

}