package com.softix.app_back.client;

import com.softix.app_back.payment.PaymentMethod;
import com.softix.app_back.person.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client")
public class Client extends TenantEntity {

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "person_id", insertable = false, updatable = false)
    private String personId;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_payment_method", length = 50)
    private PaymentMethod preferredPaymentMethod;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ClientStatus status = ClientStatus.ACTIVE;

    public Client() {}

    public Client(ClientDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
