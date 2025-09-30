package com.kingposhwolf.com.customerregistrationagentsapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingposhwolf.com.customerregistrationagentsapi.common.models.BaseModel;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.ward.WardListDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ward extends BaseModel {
    private String name;

    @ManyToOne
    @JsonIgnore
    private District district;

    public WardListDto toWardListDto(){
        return WardListDto.builder()
                .id(this.getId())
                .name(name)
                .build();
    }
}
