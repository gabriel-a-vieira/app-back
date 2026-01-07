package utils.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class TenantEntity extends RootEntity {

    @Column(name = "company_id", length = 38)
    private String companyId;

}
