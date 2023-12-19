package kr.bb.giftcard.service.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GiftCardTemplateResponse {
    private Long cardTemplateId;
    private String imageUrl;
}
