package com.softix.app_back.client;

import com.softix.app_back.payment.PaymentMethod;
import com.softix.app_back.person.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client")
public class Client extends RootEntity {

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "preferred_payment_method")
    private PaymentMethod preferredPaymentMethod;

    @Column(name = "additional_notes")
    private String additionalNotes;


    public Client() {}

    public Client(ClientDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
