package com.nhnacademy.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterPhoneNumberRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.request.ClientUpdatePrivacyRequestDto;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.exception.ClientAuthenticationFailedException;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(ClientControllerImp.class)
class ClientControllerTest {

    private static final String ID_HEADER = "X-User-Id";
    private static final String PASSWORD_HEADER = "password";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void testCreateClient() throws Exception {
        // Given
        ClientRegisterRequestDto requestDto = new ClientRegisterRequestDto(
                "test@example.com",
                "qwer1234@",
                "johndoe",
                LocalDate.of(1990, 1, 1),
                "01012345678"
        );

        ClientRegisterResponseDto responseDto = new ClientRegisterResponseDto(
                "test@example.com",
                LocalDateTime.now()
        );

        when(clientService.register(any(ClientRegisterRequestDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testLogin() throws Exception {
        // Given
        String email = "test@example.com";
        ClientLoginResponseDto responseDto = ClientLoginResponseDto.builder()
                .role(List.of("ROLE_USER"))
                .clientId(1L)
                .clientEmail(email)
                .clientPassword("password")
                .clientName("John Doe")
                .build();

        when(clientService.login(email)).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/client/login")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testGetPrivacy() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientPrivacyResponseDto responseDto = ClientPrivacyResponseDto.builder()
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .clientGrade("VIP")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .build();

        when(clientService.privacy(1L)).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/client")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testGetCouponPayments() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        ClientCouponPaymentResponseDto clientCouponPaymentResponseDto = ClientCouponPaymentResponseDto.builder()
                .clientId(1L)
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .build();

        Page<ClientCouponPaymentResponseDto> pageResponse = new PageImpl<>(List.of(clientCouponPaymentResponseDto));

        when(clientService.couponPayment(page, size)).thenReturn(pageResponse);

        // When & Then
        mockMvc.perform(get("/api/client/coupon-payment")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].clientEmail").value(clientCouponPaymentResponseDto.getClientEmail()));
    }

    @Test
    void testGetDeliveryAddresses() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientDeliveryAddressResponseDto responseDto = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(1L)
                .clientDeliveryZipCode(12345)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .build();

        when(clientService.deliveryAddress(1L)).thenReturn(List.of(responseDto));

        // When & Then
        mockMvc.perform(get("/api/client/address")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientDeliveryAddress").value(responseDto.getClientDeliveryAddress()));
    }

    @Test
    void testGetOrders() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientOrderResponseDto responseDto = ClientOrderResponseDto.builder()
                .clientId(1L)
                .clientName("John Doe")
                .clientNumbers(List.of("123-456-7890"))
                .deliveryAddresses(List.of())
                .build();

        when(clientService.order(1L)).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/client/order")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value(responseDto.getClientName()));
    }

    @Test
    void testRegisterAddress() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientRegisterAddressRequestDto requestDto = ClientRegisterAddressRequestDto.builder()
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        when(clientService.registerAddress(any(ClientRegisterAddressRequestDto.class), anyLong())).thenReturn("Success");

        // When & Then
        mockMvc.perform(post("/api/client/address")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeleteAddress() throws Exception {
        // Given
        Long addressId = 1L;

        when(clientService.deleteAddress(addressId)).thenReturn("Success");

        // When & Then
        mockMvc.perform(delete("/api/client/address")
                        .param("addressId", String.valueOf(addressId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeleteClient() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");
        headers.add(PASSWORD_HEADER, "password");

        when(clientService.deleteClient(1L, "password")).thenReturn("Success");

        // When & Then
        mockMvc.perform(delete("/api/client")
                        .header(ID_HEADER, "1")
                        .header(PASSWORD_HEADER, "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testGetPhoneNumbers() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientPhoneNumberResponseDto responseDto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(1L)
                .clientPhoneNumber("123-456-7890")
                .build();

        when(clientService.getPhoneNumbers(1L)).thenReturn(List.of(responseDto));

        // When & Then
        mockMvc.perform(get("/api/client/phone")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientPhoneNumber").value(responseDto.getClientPhoneNumber()));
    }

    @Test
    void testRegisterPhoneNumber() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientRegisterPhoneNumberRequestDto requestDto = new ClientRegisterPhoneNumberRequestDto("123-456-7890");

        when(clientService.registerPhoneNumber(any(ClientRegisterPhoneNumberRequestDto.class), anyLong())).thenReturn("Success");

        // When & Then
        mockMvc.perform(post("/api/client/phone")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeletePhoneNumber() throws Exception {
        // Given
        Long phoneNumberId = 1L;

        when(clientService.deletePhoneNumber(phoneNumberId)).thenReturn("Success");

        // When & Then
        mockMvc.perform(delete("/api/client/phone")
                        .param("phoneNumberId", String.valueOf(phoneNumberId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testUpdateClient() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientUpdatePrivacyRequestDto requestDto = new ClientUpdatePrivacyRequestDto(
                "Jane Doe",
                LocalDate.of(1991, 2, 2)
        );

        when(clientService.updateClient(anyLong(), any(String.class), any(LocalDate.class))).thenReturn("Success");

        // When & Then
        mockMvc.perform(put("/api/client")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testHandleClientEmailDuplicatesException() throws Exception {
        // Given
        doThrow(new ClientEmailDuplicatesException()).when(clientService).register(any(ClientRegisterRequestDto.class));

        ClientRegisterRequestDto requestDto = new ClientRegisterRequestDto("test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), "123-456-7890");

        // When & Then
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void testHandleNotFoundClientException() throws Exception {
        // Given
        doThrow(new NotFoundClientException("Not found")).when(clientService).privacy(anyLong());

        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        // When & Then
        mockMvc.perform(get("/api/client")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHandleClientAuthenticationFailedException() throws Exception {
        // Given
        doThrow(new ClientAuthenticationFailedException("Unauthorized")).when(clientService).deleteClient(anyLong(), any(String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");
        headers.add(PASSWORD_HEADER, "password");

        // When & Then
        mockMvc.perform(delete("/api/client")
                        .header(ID_HEADER, "1")
                        .header(PASSWORD_HEADER, "password"))
                .andExpect(status().isUnauthorized());
    }
}
