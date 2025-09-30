package com.kingposhwolf.com.customerregistrationagentsapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingposhwolf.com.customerregistrationagentsapi.common.models.BaseModel;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.location.regions.RegionListDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Region extends BaseModel {
   private String name;

   @OneToMany(mappedBy = "region")
   @JsonIgnore
   private List<District> districts;

   public RegionListDto toRegionList() {
      return RegionListDto.builder()
              .id(this.getId())
              .name(this.name)
              .build();
   }
}