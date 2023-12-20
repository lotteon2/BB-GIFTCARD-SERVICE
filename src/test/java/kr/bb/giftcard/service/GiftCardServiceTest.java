package kr.bb.giftcard.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.entity.CardTemplate;
import kr.bb.giftcard.entity.GiftCard;
import kr.bb.giftcard.repository.GiftCardRepository;
import kr.bb.giftcard.repository.GiftCardTemplateRepository;
import kr.bb.giftcard.service.response.GiftCardDetailResponse;
import kr.bb.giftcard.service.response.MyGiftCardListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class GiftCardServiceTest {
    private final Long userId = 1L;
    private final String password = "ASDFQWER";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private GiftCardService giftCardService;
    @Autowired
    private GiftCardTemplateService giftCardTemplateService;
    @Autowired
    private GiftCardTemplateRepository giftCardTemplateRepository;
    @Autowired
    private GiftCardRepository giftCardRepository;
    @Autowired
    EntityManager em;

    @MockBean
    private ChatgptService chatgptService;

    @BeforeEach
    void before() {
        String[] themes = {"BLUE", "MIX", "PINK", "PURPLE", "WHITE", "YELLOW"};
        Integer[] themeCnt = {5, 9, 9, 6, 6, 6};

        for (int i = 0; i < themes.length; i++) {
            for (int j = 0; j < themeCnt[i]; j++) {
                giftCardTemplateRepository.save(CardTemplate.builder()
                        .color(themes[i])
                        .imageUrl(themes[i] + j)
                        .build());
            }
        }

        registerGiftCard(1L);
        registerGiftCard(2L);
    }

    @DisplayName("색상별 등록된 카드 템플릿을 조회한다.")
    @Test
    void getCardTemplateListTest() {
        String[] themes = {"BLUE", "MIX", "PINK", "PURPLE", "WHITE", "YELLOW"};
        Integer[] themeCnt = {5, 9, 9, 6, 6, 6};

        for (int i = 0; i < 6; i++) {
            List<CardTemplate> result = giftCardTemplateService.getCardTemplateList(themes[i]);
            assertThat(result.size()).isEqualTo(themeCnt[i]);
        }
    }

    @DisplayName("회원은 주문 완료 시 기프트카드를 작성할 수 있다.")
    @Test
    void registerGiftCardTest() {
        GiftCard result = registerGiftCard(3L);

        assertThat(result.getCardId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    @DisplayName("회원은 본인이 작성한 카드 목록을 조회할 수 있다.")
    @Test
    void getMyCardListTest() {
        Pageable paging = PageRequest.of(0, 10);

        MyGiftCardListResponse result = giftCardService.getMyCardList(userId, paging);

        assertThat(result.getTotalCnt()).isEqualTo(2);
    }

    @DisplayName("기프트카드 id와 비밀번호가 일치할 경우 카드의 상세 정보를 조회할 수 있다.")
    @Test
    void getGiftCardDetail() {
        registerGiftCard(1L);
        em.clear();
        Long cardId = giftCardRepository.findAll().get(0).getCardId();

        String encPassword = passwordEncoder.encode(password);

        GiftCardDetailResponse detail = giftCardService.getCardDetail(cardId, encPassword);
        assertThat(detail.getCardId()).isEqualTo(cardId);
    }

    @DisplayName("기프트카드 id와 일치하지 않는 비밀번호로 상세 조회 요청 시 예외가 발생한다.")
    @Test
    void getGiftCardDetailWithInvalidPassword() {
        registerGiftCard(1L);

        Long cardId = giftCardRepository.findAll().get(0).getCardId();

        assertThatThrownBy(() -> giftCardService.getCardDetail(cardId, "QWERASDF"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("유효하지 않은 접근입니다.");
    }

    @DisplayName("기프트카드를 보낼 대상과 주문한 꽃다발의 꽃말에 따라 다른 메세지 글귀를 추천받는다.")
    @Test
    void getChatResponse() {
        String prompt = "testPrompt";

        // given
        given(chatgptService
                .sendMessage(prompt))
                .willReturn("안녕하세요! 나의 마음을 담아 이 소중한 편지를 쓰게 되어 기쁩니다. 우리의 우정은 마치 영원한 사랑 같아요. 시간이 흘러도 변하지 않는 그 따뜻함과 의미가 항상 내 마음을 감싸고 있어요.");

        // when
        String result = chatgptService.sendMessage(prompt);

        // then
        assertThat(result).isNotEmpty();
    }

    private GiftCard registerGiftCard(Long index) {
        CardTemplate template = giftCardTemplateRepository.findAll().get(0);

        GiftCardRegisterDto giftCardRegisterDto = GiftCardRegisterDto.builder()
                .orderProductId(index)
                .cardTemplateId(template.getCardTemplateId())
                .content("hello")
                .build();

        return giftCardService.registerGiftCard(giftCardRegisterDto, userId, "DELIVERY", password);
    }
}