package com.FerployDemo.ferployDemo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientType {
    PARTNER("파트너사"),
    STARTUP("창업기업"),
    INVESTOR("투자사"),
    INSTITUTION("기관"),
    CUSTOMER("고객"),
    OTHERS("기타");

    private final String description;
}