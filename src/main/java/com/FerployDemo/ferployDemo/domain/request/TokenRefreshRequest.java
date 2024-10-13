package com.FerployDemo.ferployDemo.domain.request;

import com.FerployDemo.ferployDemo.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class TokenRefreshRequest {
    private String id;
    private String nickname;
    private String refreshToken;
    private Role role;
}
