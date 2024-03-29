package kr.bb.giftcard.service.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Builder
@Getter
public class GiftCardRegisterResponse {
    private Long cardId;
    private String password;
}
