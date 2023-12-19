package kr.bb.giftcard.repository;

import kr.bb.giftcard.entity.CardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GiftCardTemplateRepository extends JpaRepository<CardTemplate, String> {
    List<CardTemplate> findByColor(String color);

    Optional<CardTemplate> findByCardTemplateId(Long cardTemplateId);

}
