package kr.bb.giftcard.message.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRegisterEvent {
    private String id;
    private String cardType;

    public static CardRegisterEvent of(String id, String cardType) {
        return CardRegisterEvent.builder()
                .id(id)
                .cardType(cardType)
                .build();
    }
}
