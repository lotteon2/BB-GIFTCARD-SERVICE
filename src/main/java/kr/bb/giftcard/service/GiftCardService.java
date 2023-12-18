package kr.bb.giftcard.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import kr.bb.giftcard.dto.GiftCardMessageDto;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.repository.GiftCardRepository;
import kr.bb.giftcard.repository.GiftCardTemplateRepository;
import kr.bb.giftcard.service.response.GiftCardDetailResponse;
import kr.bb.giftcard.service.response.GiftCardItemResponse;
import kr.bb.giftcard.service.response.MyGiftCardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GiftCardService {
    private final GiftCardTemplateRepository cardTemplateRepository;
    private final GiftCardRepository giftCardRepository;
    private final ChatgptService chatgptService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 내가 쓴 카드 목록 조회
    public MyGiftCardListResponse getMyCardList(Long userId, Pageable paging) {
        Long totalCnt = giftCardRepository.count();
        Page<GiftCardItemResponse> myCardList = giftCardRepository.findByUserId(userId, paging);

        return MyGiftCardListResponse.builder()
                .totalCnt(totalCnt)
                .myCards(myCardList)
                .build();
    }

    // 카드 상세 조회
    public GiftCardDetailResponse getCardDetail(Long cardId, String password) {
        GiftCard giftCard = giftCardRepository.findByCardId(cardId);
        CardTemplate cardTemplate = cardTemplateRepository.findByCardTemplateId(giftCard.getCardTemplate().getCardTemplateId());
        if(!passwordEncoder.matches(giftCard.getPassword(), password)) throw new RuntimeException("유효하지 않은 접근입니다.");

        return GiftCardDetailResponse.builder()
                .cardId(giftCard.getCardId())
                .imageUrl(cardTemplate.getImageUrl())
                .senderName(giftCard.getSenderName())
                .content(giftCard.getContent())
                .createdAt(giftCard.getCreatedAt())
                .build();
    }

    // 키드 메세지 작성
    @Transactional
    public GiftCard registerGiftCard(GiftCardRegisterDto giftCardRegisterDto, Long userId, String type, String tmpPassword) {
        CardTemplate cardTemplate = cardTemplateRepository.findByCardTemplateId(giftCardRegisterDto.getCardTemplateId());

        return giftCardRepository.save(GiftCard.builder()
                .orderProductId(giftCardRegisterDto.getOrderProductId())
                .userId(userId)
                .senderName(giftCardRegisterDto.getSenderName())
                .password(tmpPassword)
                .recipientPhoneNumber(giftCardRegisterDto.getRecipientPhoneNumber())
                .cardTemplate(cardTemplate)
                .type(type)
                .content(giftCardRegisterDto.getContent())
                .build());

        // TODO: 작성 완료 후 SQS
    }

    // 카드 메세지 글귀 추천
    public String getChatResponse(GiftCardMessageDto messageDto) {
        return chatgptService.sendMessage("Could you please recommend with the theme of " + messageDto.getFlower() + ", you can send emotional random letter to your " + messageDto.getTarget() + " with more than 300 characters convert korean");
    }

}
