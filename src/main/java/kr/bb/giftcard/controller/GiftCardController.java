package kr.bb.giftcard.controller;

import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.service.GiftCardTemplateService;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.dto.GiftCardMessageDto;
import kr.bb.giftcard.service.GiftCardService;
import kr.bb.giftcard.service.response.GiftCardDetailResponse;
import kr.bb.giftcard.service.response.GiftCardItemResponse;
import kr.bb.giftcard.service.response.MyGiftCardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GiftCardController {
    private final GiftCardService giftCardService;
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
        return giftCardService.getChatResponse(messageDto);
    }

    /**
     * 카드 메세지 작성
     * @param userId
     * @param type
     * @param giftCardRegisterDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<Void> registerGiftCard(@RequestHeader Long userId, @RequestParam String type, @Valid @RequestBody GiftCardRegisterDto giftCardRegisterDto) {
        String tmpPassword = UUID.randomUUID().toString().split("-")[0];
        giftCardService.registerGiftCard(giftCardRegisterDto, userId, type, tmpPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * 내가 쓴 카드 목록 조회
     * @param userId
     * @param pageable
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<MyGiftCardListResponse> getMyCardList(@RequestHeader Long userId, Pageable pageable) {
        return ResponseEntity.ok(giftCardService.getMyCardList(userId, pageable));
    }

    /**
     * 카드 상세 조회
     * @param cardId
     * @param password
     * @return
     */
    @GetMapping("/card/{cardId}/{password}")
    public ResponseEntity<GiftCardDetailResponse> getCardDetail(@PathVariable Long cardId, @PathVariable String password) {
        return ResponseEntity.ok(giftCardService.getCardDetail(cardId, password));
    }
}
