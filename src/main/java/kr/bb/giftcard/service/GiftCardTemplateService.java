package kr.bb.giftcard.service;

import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.repository.GiftCardTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GiftCardTemplateService {
    private final GiftCardTemplateRepository giftCardTemplateRepository;

    // 카드 템플릿 조회
    public List<CardTemplate> getCardTemplateList(String color) {
        return giftCardTemplateRepository.findByColor(color);
    }
}
