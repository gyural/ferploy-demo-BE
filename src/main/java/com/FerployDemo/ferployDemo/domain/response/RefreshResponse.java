package com.FerployDemo.ferployDemo.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RefreshResponse {
    private String accessToken;
    private String refreshToken;  // 필요한 경우, refresh token도 반환할 수 있도록 추가
}
