package kr.bb.giftcard.controller;

import kr.bb.giftcard.dto.GiftCardMessageDto;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.facade.GiftCardFacade;
import kr.bb.giftcard.service.GiftCardTemplateService;
import kr.bb.giftcard.service.response.GiftCardDetailResponse;
import kr.bb.giftcard.service.response.MyGiftCardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GiftCardController {
    private final GiftCardFacade giftCardFacade;
    private final GiftCardTemplateService giftCardTemplateService;

    /**
     * 카드 템플릿 조회
     * @param color
     * @return
     */
    @GetMapping("/template")
    public ResponseEntity<List<CardTemplate>> getCardTemplateList(@RequestParam String color) {
        return ResponseEntity.ok(giftCardTemplateService.getCardTemplateList((color)));
    }

    /**
     * 카드 메세지 글귀 추천
     * @param messageDto
     * @return
     */
    @PostMapping("/gpt")
    public String getRecommandCardMessage(@Valid @RequestBody GiftCardMessageDto messageDto) {
        return giftCardFacade.getChatResponse(messageDto);
    }

    /**
     * 카드 메세지 작성
     * @param userId
     * @param type
     * @param giftCardRegisterDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<GiftCard> registerGiftCard(@RequestHeader Long userId, @RequestParam String type, @Valid @RequestBody GiftCardRegisterDto giftCardRegisterDto) {

        return ResponseEntity.ok(giftCardFacade.registerGiftCard(giftCardRegisterDto, userId, type));
    }

    /**
     * 내가 쓴 카드 목록 조회
     * @param userId
     * @param pageable
     * @return
     */
    @GetMapping("/my")
    public ResponseEntity<MyGiftCardListResponse> getMyCardList(@RequestHeader Long userId, Pageable pageable) {
        return ResponseEntity.ok(giftCardFacade.getMyCardList(userId, pageable));
    }

    /**
     * 카드 상세 조회
     * @param cardId
     * @param password
     * @return
     */
    @GetMapping("/card/{cardId}/{password}")
    public ResponseEntity<GiftCardDetailResponse> getCardDetail(@PathVariable Long cardId, @PathVariable String password) {
        return ResponseEntity.ok(giftCardFacade.getCardDetail(cardId, password));
    }
}
