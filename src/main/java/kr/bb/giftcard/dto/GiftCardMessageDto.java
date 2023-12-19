package kr.bb.giftcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardMessageDto {

    @NotBlank(message = "message target cannot be empty")
    String target;

    @NotBlank(message = "flower cannot be null")
    String flower;
}
