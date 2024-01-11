package kr.bb.giftcard.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardItemResponse {
    private Long cardId;
    private String imageUrl;
    private String password;
    private String content;
    private LocalDateTime createdAt;
}
