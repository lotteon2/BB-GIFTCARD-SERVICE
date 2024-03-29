package kr.bb.giftcard.service;

import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.exception.InvalidGiftCardIdException;
import kr.bb.giftcard.exception.InvalidGiftCardTemplateException;
import kr.bb.giftcard.exception.InvalidPasswordException;
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

    // 내가 쓴 카드 목록 조회
    public MyGiftCardListResponse getMyCardList(Long userId, Pageable paging) {
        Page<GiftCardItemResponse> myCardList = giftCardRepository.findByUserId(userId, paging);

        return MyGiftCardListResponse.builder()
                .totalCnt(myCardList.getTotalElements())
                .myCards(myCardList)
                .build();
    }

    // 카드 상세 조회
    public GiftCardDetailResponse getCardDetail(Long cardId, String password) {
        GiftCard giftCard = giftCardRepository.findByCardId(cardId).orElseThrow(() -> new InvalidGiftCardIdException("존재하지 않은 카드 입니다."));

        CardTemplate cardTemplate = cardTemplateRepository.findByCardTemplateId(giftCard.getCardTemplate().getCardTemplateId())
                .orElseThrow(() -> new InvalidGiftCardTemplateException("존재하지 않는 카드 템플릿입니다."));
        if(!giftCard.getPassword().equals(password)) throw new InvalidPasswordException("유효하지 않은 접근입니다.");

        return GiftCardDetailResponse.builder()
                .cardId(giftCard.getCardId())
                .imageUrl(cardTemplate.getImageUrl())
                .content(giftCard.getContent())
                .createdAt(giftCard.getCreatedAt())
                .build();
    }

    // 카드 메세지 작성
    @Transactional
    public GiftCard registerGiftCard(GiftCardRegisterDto giftCardRegisterDto, Long userId, String type, String tmpPassword) {
        CardTemplate cardTemplate = cardTemplateRepository.findByCardTemplateId(giftCardRegisterDto.getCardTemplateId())
                .orElseThrow(() -> new InvalidGiftCardTemplateException("존재하지 않는 카드 템플릿입니다."));

        return giftCardRepository.save(GiftCard.builder()
                .orderProductId(giftCardRegisterDto.getOrderProductId())
                .userId(userId)
                .password(tmpPassword)
                .cardTemplate(cardTemplate)
                .type(type)
                .content(giftCardRegisterDto.getContent())
                .build());
    }

    // 카드 메세지 글귀 추천

}
