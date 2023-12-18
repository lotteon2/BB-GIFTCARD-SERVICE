package kr.bb.giftcard.repository;

import kr.bb.giftcard.entity.CardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftCardTemplateRepository extends JpaRepository<CardTemplate, String> {
    List<CardTemplate> findByColor(String color);

    CardTemplate findByCardTemplateId(Long cardTemplateId);

}
