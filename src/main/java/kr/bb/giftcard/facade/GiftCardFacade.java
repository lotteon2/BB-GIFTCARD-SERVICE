package kr.bb.giftcard.facade;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import kr.bb.giftcard.dto.GiftCardMessageDto;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.message.CardSNSPublisher;
import kr.bb.giftcard.message.event.CardRegisterEvent;
import kr.bb.giftcard.service.GiftCardService;
import kr.bb.giftcard.service.response.GiftCardDetailResponse;
import kr.bb.giftcard.service.response.GiftCardRegisterResponse;
import kr.bb.giftcard.service.response.MyGiftCardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GiftCardFacade {
    private final GiftCardService giftCardService;
    private final CardSNSPublisher cardSNSPublisher;
    private final ChatgptService chatgptService;

    public MyGiftCardListResponse getMyCardList(Long userId, Pageable paging) {
        return giftCardService.getMyCardList(userId, paging);
    }

    public GiftCardDetailResponse getCardDetail(Long cardId, String password) {
        return giftCardService.getCardDetail(cardId, password);
    }

    public GiftCardRegisterResponse registerGiftCard(GiftCardRegisterDto giftCardRegisterDto, Long userId, String type) {
        String tmpPassword = UUID.randomUUID().toString().split("-")[0];
        GiftCard registered = giftCardService.registerGiftCard(giftCardRegisterDto, userId, type, tmpPassword);
        GiftCardRegisterResponse result = GiftCardRegisterResponse.builder().cardId(registered.getCardId()).password(registered.getPassword()).build();

        cardSNSPublisher.publish(CardRegisterEvent.of(giftCardRegisterDto.getOrderProductId(), type));
        return result;
    }

    // 카드 메세지 글귀 추천
    public String getChatResponse(GiftCardMessageDto messageDto) {
        String message = "Can you translate more than 300 characters of emotional letter to my " +
                messageDto.getTarget() +
                " on the subject of " +
                messageDto.getFlower() +
                " into html code at the same time as translating into Korean? Please print out only the internal content without the 'body' tag.";
        return chatgptService.sendMessage(message);
    }
}
