package kr.bb.giftcard.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.bb.giftcard.message.event.CardRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
@RequiredArgsConstructor
public class CardSNSPublisher {
    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.sns.card-register-event.arn}")
    private String arn;

    public void publish(CardRegisterEvent cardRegisterEvent) {
        try {
            PublishRequest publishRequest = PublishRequest.builder()
                    .message(objectMapper.writeValueAsString(cardRegisterEvent))
                    .topicArn(arn)
                    .build();
            snsClient.publish(publishRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
