package com.nbcamp.b4trello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 아래 설정을 등록하여 활성화
@EnableJpaAuditing // 시간 자동으로 변경 가능하도록 함.
public class JpaConfig {
}
