package com.kingposhwolf.com.customerregistrationagentsapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kingposhwolf.com.customerregistrationagentsapi.common.models.BaseModel;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.district.DistrictListDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class District extends BaseModel {
    private String name;

    @ManyToOne
    @JsonIgnore
    private Region region;

    @OneToMany(mappedBy = "district")
    private List<Ward> wards;

    public DistrictListDto toDistrictList(){
        return DistrictListDto.builder()
                .id(this.getId())
                .name(name)
                .build();
    }
}
