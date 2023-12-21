package kr.bb.giftcard.service.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GiftCardDetailResponse {
    private Long cardId;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
}