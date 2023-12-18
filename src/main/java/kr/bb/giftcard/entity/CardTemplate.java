package kr.bb.giftcard.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "card_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_template_id")
    private Long cardTemplateId;

    @Column(name = "color")
    private String color;

    @Column(name = "image_url")
    private String imageUrl;
}
