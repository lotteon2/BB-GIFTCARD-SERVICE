package kr.bb.giftcard.service.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Builder
@Getter
public class GiftCardItemResponse {
    private Long cardId;
    private String imageUrl;
    private String password;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public GiftCardItemResponse(Long cardId, String imageUrl, String password, String content, LocalDateTime createdAt) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        this.cardId = cardId;
        this.imageUrl = imageUrl;
        this.password = passwordEncoder.encode(password);
        this.content = content;
        this.createdAt = createdAt;
    }
}
