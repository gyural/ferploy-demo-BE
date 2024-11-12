package com.FerployDemo.ferployDemo.dto;

import com.FerployDemo.ferployDemo.domain.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class NameCardDTO {
    private String name;
    private String companyName;
    private String position;
    private String mobileNumber;
    private String email;
    private String contactNumber;
    private ClientType clientType; // enum으로 수정된 고객 타입
    private String place;
    private String memo;
    private String address;
    private String homepage;
    private String nameCardImg;
    private String profileImg;
    private String bgColor;
    private String textColor;
    private String greetingMessage;
}
