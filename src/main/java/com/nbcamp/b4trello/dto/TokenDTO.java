package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDTO {

    /**
     * @String grantType  : JWT에 대한 인증 타입. Bearer
     * @String accessToken : 엑세스 토큰 저장
     * @String refreshToken : 리프레쉬 토큰 저장
     */
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
