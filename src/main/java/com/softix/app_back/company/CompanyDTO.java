package com.softix.app_back.company;

import com.softix.app_back.address.AddressDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CompanyDTO {

    private String id;
    private String legalName;
    private String tradeName;
    private String cnpj;
    private CompanyType type;

    private AddressDTO address;

    private String imageUrl;
    private String instagramUrl;
    private String facebookUrl;
    private String websiteUrl;
    private String tiktokUrl;
    
    public CompanyDTO() {}

    public CompanyDTO(Company company) {
        BeanUtils.copyProperties(company, this);
    }

}
