package com.nhnacademy.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.dto.request.*;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.exception.*;
import com.nhnacademy.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testCreateClient() throws Exception {
        ClientRegisterRequestDto requestDto = new ClientRegisterRequestDto(
                "test@example.com", "qwer1234@", "johndoe", LocalDate.of(1990, 1, 1), "01012345678");

        ClientRegisterResponseDto responseDto = new ClientRegisterResponseDto("test@example.com", LocalDateTime.now());

        when(clientService.register(any(ClientRegisterRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testCreateOauthClient() throws Exception {
        ClientOAuthRegisterRequestDto requestDto = new ClientOAuthRegisterRequestDto(
                "oauthClientId",
                "oauthClientSecret",
                LocalDate.now()
        );
        when(clientService.oauthRegister(any(ClientOAuthRegisterRequestDto.class))).thenReturn("Success");

        mockMvc.perform(post("/api/oauth/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testLogin() throws Exception {
        String email = "test@example.com";
        ClientLoginResponseDto responseDto = ClientLoginResponseDto.builder()
                .role(List.of("ROLE_USER"))
                .clientId(1L)
                .clientEmail(email)
                .clientPassword("password")
                .clientName("John Doe")
                .build();

        when(clientService.login(email)).thenReturn(responseDto);

        mockMvc.perform(get("/api/client/login")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testGetPrivacy() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientPrivacyResponseDto responseDto = ClientPrivacyResponseDto.builder()
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .clientGrade("VIP")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .build();

        when(clientService.privacy(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/client")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientEmail").value(responseDto.getClientEmail()));
    }

    @Test
    void testGetCouponPayments() throws Exception {
        int page = 0;
        int size = 10;

        ClientCouponPaymentResponseDto clientCouponPaymentResponseDto = ClientCouponPaymentResponseDto.builder()
                .clientId(1L)
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .build();

        Page<ClientCouponPaymentResponseDto> pageResponse = new PageImpl<>(List.of(clientCouponPaymentResponseDto));

        when(clientService.couponPayment(page, size)).thenReturn(pageResponse);

        mockMvc.perform(get("/api/client/coupon-payment")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].clientEmail").value(clientCouponPaymentResponseDto.getClientEmail()));
    }

    @Test
    void testGetDeliveryAddresses() throws Exception {
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

        mockMvc.perform(get("/api/client/address")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientDeliveryAddress").value(responseDto.getClientDeliveryAddress()));
    }

    @Test
    void testGetOrders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientOrderResponseDto responseDto = ClientOrderResponseDto.builder()
                .clientId(1L)
                .clientName("John Doe")
                .clientNumbers(List.of("123-456-7890"))
                .deliveryAddresses(List.of())
                .build();

        when(clientService.order(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/client/order")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value(responseDto.getClientName()));
    }

    @Test
    void testRegisterAddress() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientRegisterAddressRequestDto requestDto = ClientRegisterAddressRequestDto.builder()
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        when(clientService.registerAddress(any(ClientRegisterAddressRequestDto.class), anyLong())).thenReturn("Success");

        mockMvc.perform(post("/api/client/address")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeleteAddress() throws Exception {
        Long addressId = 1L;

        when(clientService.deleteAddress(addressId)).thenReturn("Success");

        mockMvc.perform(delete("/api/client/address")
                        .param("addressId", String.valueOf(addressId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeleteClient() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");
        headers.add(PASSWORD_HEADER, "password");

        when(clientService.deleteClient(1L, "password")).thenReturn("Success");

        mockMvc.perform(delete("/api/client")
                        .header(ID_HEADER, "1")
                        .header(PASSWORD_HEADER, "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testGetPhoneNumbers() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientPhoneNumberResponseDto responseDto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(1L)
                .clientPhoneNumber("123-456-7890")
                .build();

        when(clientService.getPhoneNumbers(1L)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/client/phone")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientPhoneNumber").value(responseDto.getClientPhoneNumber()));
    }

    @Test
    void testRegisterPhoneNumber() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientRegisterPhoneNumberRequestDto requestDto = new ClientRegisterPhoneNumberRequestDto("123-456-7890");

        when(clientService.registerPhoneNumber(any(ClientRegisterPhoneNumberRequestDto.class), anyLong())).thenReturn("Success");

        mockMvc.perform(post("/api/client/phone")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeletePhoneNumber() throws Exception {
        Long phoneNumberId = 1L;

        when(clientService.deletePhoneNumber(phoneNumberId)).thenReturn("Success");

        mockMvc.perform(delete("/api/client/phone")
                        .param("phoneNumberId", String.valueOf(phoneNumberId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testUpdateClient() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientUpdatePrivacyRequestDto requestDto = new ClientUpdatePrivacyRequestDto(
                "Jane Doe", LocalDate.of(1991, 2, 2));

        when(clientService.updateClient(anyLong(), any(String.class), any(LocalDate.class))).thenReturn("Success");

        mockMvc.perform(put("/api/client")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testChangePasswordClient() throws Exception {
        ClientChangePasswordRequestDto requestDto = new ClientChangePasswordRequestDto(
                "test@example.com", "newPassword123", "token123");

        when(clientService.changePasswordClient(any(String.class), any(String.class), any(String.class)))
                .thenReturn("Success");

        mockMvc.perform(put("/api/client/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testRecoveryClient() throws Exception {
        ClientRecoveryRequestDto requestDto = new ClientRecoveryRequestDto(
                "test@example.com", "token123");

        when(clientService.recveryClinet(any(String.class), any(String.class)))
                .thenReturn("Success");

        mockMvc.perform(put("/api/client/recovery-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testRecoveryOauthClient() throws Exception {
        String email = "test@example.com";

        when(clientService.recveryOauthClinet(any(String.class)))
                .thenReturn("Success");

        mockMvc.perform(put("/api/client/recovery-oauth-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(email))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testGetThisMonthBirthClient() throws Exception {
        List<Long> clientIds = List.of(1L, 2L, 3L);

        when(clientService.getThisMonthBirthClient()).thenReturn(clientIds);

        mockMvc.perform(get("/api/client/birth-coupon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(clientIds.get(0)))
                .andExpect(jsonPath("$[1]").value(clientIds.get(1)))
                .andExpect(jsonPath("$[2]").value(clientIds.get(2)));
    }

    @Test
    void testHandleClientEmailDuplicatesException() throws Exception {
        doThrow(new ClientEmailDuplicatesException()).when(clientService).register(any(ClientRegisterRequestDto.class));

        ClientRegisterRequestDto requestDto = new ClientRegisterRequestDto(
                "test@example.com", "qwer1234@", "johndoe", LocalDate.of(1990, 1, 1), "01012345678");

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void testHandleNotFoundClientException() throws Exception {
        doThrow(new NotFoundClientException("Not found")).when(clientService).privacy(anyLong());

        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        mockMvc.perform(get("/api/client")
                        .header(ID_HEADER, "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHandleClientAuthenticationFailedException() throws Exception {
        doThrow(new ClientAuthenticationFailedException("Unauthorized")).when(clientService).deleteClient(anyLong(), any(String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");
        headers.add(PASSWORD_HEADER, "password");

        mockMvc.perform(delete("/api/client")
                        .header(ID_HEADER, "1")
                        .header(PASSWORD_HEADER, "password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testHandleClientDeletedException() throws Exception {
        doThrow(new ClientDeletedException("Gone")).when(clientService).login(any(String.class));

        mockMvc.perform(get("/api/client/login")
                        .param("email", "test@example.com"))
                .andExpect(status().isGone());
    }

    @Test
    void testHandleClientAddressOutOfRangeException() throws Exception {
        doThrow(new ClientAddressOutOfRangeException("Bad Request")).when(clientService).registerAddress(any(ClientRegisterAddressRequestDto.class), anyLong());

        HttpHeaders headers = new HttpHeaders();
        headers.add(ID_HEADER, "1");

        ClientRegisterAddressRequestDto requestDto = ClientRegisterAddressRequestDto.builder()
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        mockMvc.perform(post("/api/client/address")
                        .header(ID_HEADER, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}