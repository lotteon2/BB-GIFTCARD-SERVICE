package kr.bb.giftcard.repository;

import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.service.response.GiftCardItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

    @Query(value = "SELECT new kr.bb.giftcard.service.response.GiftCardItemResponse(gc.cardId, gc.password, gc.content, gc.createdAt) FROM GiftCard gc WHERE gc.userId = :userId ORDER BY gc.createdAt DESC")
    Page<GiftCardItemResponse> findByUserId(Long userId, Pageable paging);

    GiftCard findByCardId(Long cardId);
}
