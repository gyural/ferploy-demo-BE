package com.FerployDemo.ferployDemo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientType {
    파트너사("파트너사"),
    창업기업("창업기업"),
    투자사("투자사"),
    기관("기관"),
    고객("고객"),
    기타("기타");

    private final String description;
}