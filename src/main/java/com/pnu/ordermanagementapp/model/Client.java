package com.pnu.ordermanagementapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {

    private String firstName;

    private String lastName;

    private String email;

}
