package kr.bb.giftcard.repository;

import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.service.response.GiftCardItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

    @Query(value = "SELECT new kr.bb.giftcard.service.response.GiftCardItemResponse(gc.cardId, ct.imageUrl, gc.password, gc.content, gc.createdAt) FROM GiftCard gc INNER JOIN CardTemplate ct ON gc.cardTemplate.cardTemplateId =ct.cardTemplateId WHERE gc.userId = :userId ORDER BY gc.createdAt DESC")
    Page<GiftCardItemResponse> findByUserId(Long userId, Pageable paging);

    Optional<GiftCard> findByCardId(Long cardId);
}
