package com.FerployDemo.ferployDemo.domain.request;

import com.FerployDemo.ferployDemo.domain.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostCreateNameCardRequest {
    private String name;
    private String position;
    private String companyName;
    private String savedDate;
    private String email;
    private String place;
    private String memo;
    private String mobileNumber;
    private ClientType clientType;  // ClientType 필드 추가
    private String bgColor;
    private String textColor;
}
