//package com.kingposhwolf.com.customerregistrationagentsapi.controllers;
//
//import com.kingposhwolf.com.customerregistrationagentsapi.common.controllers.BaseController;
//import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.customer.CreateCustomerDto;
//import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.customer.CustomerListDto;
//import com.kingposhwolf.com.customerregistrationagentsapi.models.Customer;
//import com.kingposhwolf.com.customerregistrationagentsapi.services.CustomerService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/customers")
//public class CustomerController extends BaseController<Customer, Long, CreateCustomerDto, CreateCustomerDto, CustomerListDto, CustomerListDto, CustomerService> {
//    public CustomerController(CustomerService service) {
//        super(service);
//    }
//}
package com.kingposhwolf.com.customerregistrationagentsapi.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.common.controllers.BaseController;
import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.input.customer.CreateCustomerDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.customer.CustomerListDto;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated.PaginatedResponse;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Customer;
import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import com.kingposhwolf.com.customerregistrationagentsapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController extends BaseController<Customer, Long, CreateCustomerDto, CreateCustomerDto, CustomerListDto, CustomerListDto, CustomerService> {

    public CustomerController(CustomerService service) {
        super(service);
    }

    @GetMapping("/my-registrations")
    public ResponseEntity<PaginatedResponse<CustomerListDto>> getMyCustomers(
            @AuthenticationPrincipal User user,
            @ModelAttribute QueryParamsDTO queryParams) {

        PaginatedResponse<CustomerListDto> response = service.findByRegisteredBy(user, queryParams);
        return ResponseEntity.ok(response);
    }
}