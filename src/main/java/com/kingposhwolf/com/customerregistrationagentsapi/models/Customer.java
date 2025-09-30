package com.kingposhwolf.com.customerregistrationagentsapi.models;

import com.kingposhwolf.com.customerregistrationagentsapi.common.models.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer extends BaseModel {
    private String name;
    private String nida;
    private Date dob;

    @ManyToOne
    private User registeredBy;

    @ManyToOne
    private Ward ward;
}
