package utils.model.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import utils.model.RootEntity;
import utils.security.SecurityUtils;

@Data
@MappedSuperclass
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(name = "companyId", type = String.class)
)
@Filter(
        name = "tenantFilter",
        condition = "company_id = :companyId"
)
public class TenantEntity extends RootEntity {

    @Column(name = "company_id", length = 38)
    private String companyId;

    @PrePersist
    protected void prePersistTenant() {

        super.prePersistRoot();

        if (SecurityUtils.currentUser() != null) {
            this.companyId = SecurityUtils.companyId();
        }

    }

}