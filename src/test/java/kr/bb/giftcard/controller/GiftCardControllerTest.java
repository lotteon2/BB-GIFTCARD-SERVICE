package kr.bb.giftcard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.bb.giftcard.dto.GiftCardMessageDto;
import kr.bb.giftcard.dto.GiftCardRegisterDto;
import kr.bb.giftcard.facade.GiftCardFacade;
import kr.bb.giftcard.service.GiftCardService;
import kr.bb.giftcard.service.GiftCardTemplateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = GiftCardController.class)
class GiftCardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GiftCardFacade giftCardFacade;
    @MockBean
    private GiftCardTemplateService giftCardTemplateService;

    @DisplayName("기프트카드 글귀 추천 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void recommendCardMessageRequestPropertiesCannotBeNullAndEmpty() throws Exception {
        GiftCardMessageDto giftCardMessageDto = GiftCardMessageDto.builder()
                .target("family")
                .flower("eternal love")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/gpt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(giftCardMessageDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("기프트카드 글귀 추천 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void recommendCardMessageRequestPropertiesCannotBeNullAndEmpty2() throws Exception {
        GiftCardMessageDto giftCardMessageDto = GiftCardMessageDto.builder()
                .target(null)
                .flower("eternal love")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/gpt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(giftCardMessageDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("카드 메세지 작성 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void giftCardCreatePropertiesCannotBeNullAndEmpty() throws Exception {
        GiftCardRegisterDto giftCardRegisterDto = createRegisterDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(giftCardRegisterDto))
                .header("userId", 1L).param("type", "DELIVERY"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("카드 메세지 작성 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void giftCardCreatePropertiesCannotBeNullAndEmpty2() throws Exception {
        GiftCardRegisterDto giftCardRegisterDto = createRegisterDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(giftCardRegisterDto))
                        .param("type", "DELIVERY"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("카드 메세지 작성 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void giftCardCreatePropertiesCannotBeNullAndEmpty3() throws Exception {
        GiftCardRegisterDto giftCardRegisterDto = createRegisterDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(giftCardRegisterDto))
                        .header("userId", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("카드 메세지 작성 시 요청값은 모두 null이나 empty가 아니여야 한다.")
    @Test
    void giftCardCreatePropertiesCannotBeNullAndEmpty4() throws Exception {
        GiftCardRegisterDto giftCardRegisterDto = GiftCardRegisterDto.builder()
                .orderProductId(1L)
                .cardTemplateId(1L)
                .content("")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(giftCardRegisterDto))
                        .header("userId", 1L).param("type", "DELIVERY"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private GiftCardRegisterDto createRegisterDto() {
        return GiftCardRegisterDto.builder()
                .orderProductId(1L)
                .cardTemplateId(1L)
                .content("test content")
                .build();
    }
}