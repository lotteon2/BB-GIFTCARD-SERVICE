package kr.bb.giftcard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "gift_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class GiftCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_template_id", nullable = false)
    private CardTemplate cardTemplate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_product_id", nullable = false)
    private Long orderProductId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "recipient_phone_number", nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "type", nullable = false)
    private String type;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
