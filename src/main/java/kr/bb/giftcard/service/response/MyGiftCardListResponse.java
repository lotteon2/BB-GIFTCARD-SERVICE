package kr.bb.giftcard.service.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class MyGiftCardListResponse {
    private Long totalCnt;
    private Page<GiftCardItemResponse> myCards;
}
