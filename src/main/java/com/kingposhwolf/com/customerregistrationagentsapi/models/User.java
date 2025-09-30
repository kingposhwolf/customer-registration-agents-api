package com.kingposhwolf.com.customerregistrationagentsapi.models;

import com.kingposhwolf.com.customerregistrationagentsapi.common.models.BaseModel;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.auth.UserDetailsDto;
import com.kingposhwolf.com.customerregistrationagentsapi.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel implements UserDetails {
    private String username;
    private String password;
    private Role role;
    private String phoneNumber;

    @OneToOne
    private Ward ward;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority)()-> role.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public UserDetailsDto toUserDetails() {
        UserDetailsDto.LocationValue wardValue = null;
        UserDetailsDto.LocationValue districtValue = null;
        UserDetailsDto.LocationValue regionValue = null;

        if (this.ward != null) {
            wardValue = UserDetailsDto.LocationValue.builder()
                    .id(this.ward.getId())
                    .name(this.ward.getName())
                    .build();

            if (this.ward.getDistrict() != null) {
                districtValue = UserDetailsDto.LocationValue.builder()
                        .id(this.ward.getDistrict().getId())
                        .name(this.ward.getDistrict().getName())
                        .build();

                if (this.ward.getDistrict().getRegion() != null) {
                    regionValue = UserDetailsDto.LocationValue.builder()
                            .id(this.ward.getDistrict().getRegion().getId())
                            .name(this.ward.getDistrict().getRegion().getName())
                            .build();
                }
            }
        }

        UserDetailsDto.Location location = UserDetailsDto.Location.builder()
                .region(regionValue)
                .district(districtValue)
                .ward(wardValue)
                .build();

        return UserDetailsDto.builder()
                .id(this.getId())
                .name(this.getUsername())
                .phone(this.getPhoneNumber())
                .location(location)
                .status(this.isEnabled())
                .build();
    }
}