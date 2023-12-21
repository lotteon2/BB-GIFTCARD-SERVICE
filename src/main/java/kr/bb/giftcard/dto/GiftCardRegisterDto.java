package kr.bb.giftcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardRegisterDto {
    @NotNull(message = "orderProductId cannot be null")
    private Long orderProductId;

    @NotNull(message = "cardTemplateId cannot be null")
    private Long cardTemplateId;

    @NotBlank(message = "content cannot be empty")
    private String content;
}
