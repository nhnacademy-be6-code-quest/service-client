package com.nhnacademy.client.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.dto.message.ClientLoginMessageDto;
import com.nhnacademy.client.dto.request.*;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.entity.*;
import com.nhnacademy.client.exception.*;
import com.nhnacademy.client.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientRoleRepository clientRoleRepository;
    @Mock
    private ClientGradeRepository clientGradeRepository;
    @Mock
    private ClientNumberRepository clientNumberRepository;
    @Mock
    private ClientDeliveryAddressRepository clientDeliveryAddressRepository;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private ClientServiceImp clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void testRegister() {
        // Given
        ClientRegisterRequestDto registerInfo = new ClientRegisterRequestDto("test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), "123-456-7890");

        when(clientRepository.findByClientEmail(registerInfo.getClientEmail())).thenReturn(null);
        when(clientGradeRepository.findByClientGradeName("common")).thenReturn(new ClientGrade(1L, "common", 0, 0));
        when(passwordEncoder.encode(registerInfo.getClientPassword())).thenReturn("encodedPassword");
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(new Role(1, "ROLE_USER"));

        // When
        ClientRegisterResponseDto response = clientService.register(registerInfo);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getClientEmail()).isEqualTo("test@example.com");
        verify(clientRepository).save(any(Client.class));
        verify(clientNumberRepository).save(any(ClientNumber.class));
        verify(clientRoleRepository).save(any(ClientRole.class));
    }

    @Test
    void testRegister_ThrowsClientEmailDuplicatesException() {
        // Given
        ClientRegisterRequestDto registerInfo = new ClientRegisterRequestDto("test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), "123-456-7890");

        when(clientRepository.findByClientEmail(registerInfo.getClientEmail())).thenReturn(new Client());

        // When & Then
        assertThatThrownBy(() -> clientService.register(registerInfo))
                .isInstanceOf(ClientEmailDuplicatesException.class);
    }

    @Test
    void testLogin() {
        // Given
        String email = "test@example.com";
        Client client = new Client(1L, new ClientGrade(), email, "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findByClientEmail(email)).thenReturn(client);
        when(clientRoleRepository.findRolesByClient(client)).thenReturn(List.of(new Role(1, "ROLE_USER")));

        // When
        ClientLoginResponseDto response = clientService.login(email);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getClientEmail()).isEqualTo(email);
    }

    @Test
    void testLogin_ThrowsNotFoundClientException() {
        // Given
        String email = "test@example.com";
        when(clientRepository.findByClientEmail(email)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> clientService.login(email))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testPrivacy() {
        // Given
        Long id = 1L;
        Client client = new Client(id, new ClientGrade(1L, "VIP", 0, 0), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // When
        ClientPrivacyResponseDto response = clientService.privacy(id);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getClientEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testPrivacy_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.privacy(id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testCouponPayment() {
        // Given
        int page = 0;
        int size = 10;
        Client client = new Client(1L, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        Page<Client> clientPage = new PageImpl<>(List.of(client));
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(clientPage);

        // When
        Page<ClientCouponPaymentResponseDto> response = clientService.couponPayment(page, size);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
    }

    @Test
    void testDeliveryAddress() {
        // Given
        Long id = 1L;
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientDeliveryAddress address = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientDeliveryAddressRepository.findAllByClient(client)).thenReturn(List.of(address));

        // When
        List<ClientDeliveryAddressResponseDto> response = clientService.deliveryAddress(id);

        // Then
        assertThat(response).isNotNull().hasSize(1);
    }

    @Test
    void testDeliveryAddress_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.deliveryAddress(id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testGetPhoneNumbers() {
        // Given
        Long id = 1L;
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber phoneNumber = new ClientNumber(1L, client, "123-456-7890");
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientNumberRepository.findAllByClient(client)).thenReturn(List.of(phoneNumber));

        // When
        List<ClientPhoneNumberResponseDto> response = clientService.getPhoneNumbers(id);

        // Then
        assertThat(response).isNotNull().hasSize(1);
    }

    @Test
    void testGetPhoneNumbers_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.getPhoneNumbers(id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testOrder() {
        // Given
        Long id = 1L;
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber phoneNumber = new ClientNumber(1L, client, "123-456-7890");
        ClientDeliveryAddress address = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientNumberRepository.findAllByClient(client)).thenReturn(List.of(phoneNumber));
        when(clientDeliveryAddressRepository.findAllByClient(client)).thenReturn(List.of(address));

        // When
        ClientOrderResponseDto response = clientService.order(id);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getClientId()).isEqualTo(id);
    }

    @Test
    void testOrder_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.order(id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testRegisterAddress() {
        // Given
        Long id = 1L;
        ClientRegisterAddressRequestDto requestDto = new ClientRegisterAddressRequestDto("123 Main St", "Apt 4B", "Home", 12345);
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // When
        String response = clientService.registerAddress(requestDto, id);

        // Then
        assertThat(response).isEqualTo("Success");
        verify(clientDeliveryAddressRepository).save(any(ClientDeliveryAddress.class));
    }

    @Test
    void testRegisterAddress_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        ClientRegisterAddressRequestDto requestDto = new ClientRegisterAddressRequestDto("123 Main St", "Apt 4B", "Home", 12345);
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.registerAddress(requestDto, id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testRegisterPhoneNumber() {
        // Given
        Long id = 1L;
        ClientRegisterPhoneNumberRequestDto requestDto = new ClientRegisterPhoneNumberRequestDto("123-456-7890");
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // When
        String response = clientService.registerPhoneNumber(requestDto, id);

        // Then
        assertThat(response).isEqualTo("Success");
        verify(clientNumberRepository).save(any(ClientNumber.class));
    }

    @Test
    void testRegisterPhoneNumber_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        ClientRegisterPhoneNumberRequestDto requestDto = new ClientRegisterPhoneNumberRequestDto("123-456-7890");
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.registerPhoneNumber(requestDto, id))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testDeleteAddress() {
        // Given
        Long addressId = 1L;

        // When
        String response = clientService.deleteAddress(addressId);

        // Then
        assertThat(response).isEqualTo("Success");
        verify(clientDeliveryAddressRepository).deleteById(addressId);
    }

    @Test
    void testDeletePhoneNumber() {
        // Given
        Long phoneNumberId = 1L;

        // When
        String response = clientService.deletePhoneNumber(phoneNumberId);

        // Then
        assertThat(response).isEqualTo("Success");
        verify(clientNumberRepository).deleteById(phoneNumberId);
    }

    @Test
    void testDeleteClient() {
        // Given
        Long id = 1L;
        String password = "password";
        Client client = new Client(id, new ClientGrade(), "test@example.com", password, "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(password, client.getClientPassword())).thenReturn(true);

        // When
        String response = clientService.deleteClient(id, password);

        // Then
        assertThat(response).isEqualTo("Success");
        assertThat(client.isDeleted()).isTrue();
        verify(clientRepository).save(client);
    }

    @Test
    void testDeleteClient_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        String password = "password";
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.deleteClient(id, password))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testDeleteClient_ThrowsClientAuthenticationFailedException() {
        // Given
        Long id = 1L;
        String password = "password";
        Client client = new Client(id, new ClientGrade(), "test@example.com", "wrongPassword", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(password, client.getClientPassword())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> clientService.deleteClient(id, password))
                .isInstanceOf(ClientAuthenticationFailedException.class);
    }

    @Test
    void testUpdateClient() {
        // Given
        Long id = 1L;
        String name = "Jane Doe";
        LocalDate birth = LocalDate.of(1991, 2, 2);
        Client client = new Client(id, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // When
        String response = clientService.updateClient(id, name, birth);

        // Then
        assertThat(response).isEqualTo("Success");
        assertThat(client.getClientName()).isEqualTo(name);
        assertThat(client.getClientBirth()).isEqualTo(birth);
        verify(clientRepository).save(client);
    }

    @Test
    void testUpdateClient_ThrowsNotFoundClientException() {
        // Given
        Long id = 1L;
        String name = "Jane Doe";
        LocalDate birth = LocalDate.of(1991, 2, 2);
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.updateClient(id, name, birth))
                .isInstanceOf(NotFoundClientException.class);
    }

    @Test
    void testReceiveMessage() throws IOException {
        // Given
        String message = "{\"clientId\":1,\"lastLoginDate\":\"2023-06-28T12:34:56\"}";
        ClientLoginMessageDto messageDto = new ClientLoginMessageDto(1L, LocalDateTime.of(2023, 6, 28, 12, 34, 56));
        Client client = new Client(1L, new ClientGrade(), "test@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);

        when(objectMapper.readValue(message, ClientLoginMessageDto.class)).thenReturn(messageDto);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // When
        clientService.receiveMessage(message);

        // Then
        assertThat(client.getLastLoginDate()).isEqualTo(messageDto.getLastLoginDate());
        verify(clientRepository).save(client);
    }

    @Test
    @SuppressWarnings("java:S1874")
    void testReceiveMessage_ThrowsRabbitMessageConvertException() throws IOException {
        // Given
        String message = "invalid message";
        when(objectMapper.readValue(message, ClientLoginMessageDto.class)).thenThrow(new JsonMappingException("invalid message"));

        // When & Then
        assertThatThrownBy(() -> clientService.receiveMessage(message))
                .isInstanceOf(RabbitMessageConvertException.class);
    }

    // Additional tests for new methods

    @Test
    void testChangePasswordClient() {
        // Given
        String email = "test@example.com";
        String newPassword = "newPassword123";
        String token = "validToken";
        Client client = new Client(1L, new ClientGrade(), email, "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);

        when(clientRepository.findByClientEmail(email)).thenReturn(client);
        when(redisTemplate.opsForHash().get("change-password", token)).thenReturn("valid");

        // When
        String response = clientService.changePasswordClient(email, newPassword, token);

        // Then
        assertThat(response).isEqualTo("Success");
        verify(clientRepository).save(client);
    }

    @Test
    void testChangePasswordClient_ThrowsBadRequestException() {
        // Given
        String email = "test@example.com";
        String newPassword = "newPassword123";
        String token = "invalidToken";

        when(clientRepository.findByClientEmail(email)).thenReturn(new Client());
        when(hashOperations.get("change-password", token)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> clientService.changePasswordClient(email, newPassword, token))
                .isInstanceOf(ClientAuthenticationFailedException.class);
    }

    @Test
    void testRecveryClinet() {
        // Given
        String email = "test@example.com";
        String token = "validToken";
        Client client = new Client(1L, new ClientGrade(), email, "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), true, null);

        when(clientRepository.findByClientEmail(email)).thenReturn(client);
        when(redisTemplate.opsForHash().get("recovery-account", token)).thenReturn("valid");

        // When
        String response = clientService.recveryClinet(email, token);

        // Then
        assertThat(response).isEqualTo("Success");
        assertThat(client.isDeleted()).isFalse();
        verify(clientRepository).save(client);
    }

    @Test
    void testRecveryClinet_ThrowsBadRequestException() {
        // Given
        String email = "test@example.com";
        String token = "invalidToken";

        when(clientRepository.findByClientEmail(email)).thenReturn(new Client());
        when(redisTemplate.opsForHash().get("recovery-account", token)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> clientService.recveryClinet(email, token))
                .isInstanceOf(ClientAuthenticationFailedException.class);
    }

    @Test
    void testRecveryOauthClinet() {
        // Given
        String email = "test@example.com";
        Client client = new Client(1L, new ClientGrade(), email, "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), true, null);

        when(clientRepository.findByClientEmail(email)).thenReturn(client);

        // When
        String response = clientService.recveryOauthClinet(email);

        // Then
        assertThat(response).isEqualTo("Success");
        assertThat(client.isDeleted()).isFalse();
        verify(clientRepository).save(client);
    }

    @Test
    void testGetThisMonthBirthClient() {
        // Given
        List<Long> clientIds = List.of(1L, 2L, 3L);

        when(clientRepository.findClientsWithBirthInCurrentMonth()).thenReturn(clientIds);

        // When
        List<Long> response = clientService.getThisMonthBirthClient();

        // Then
        assertThat(response).isNotNull().hasSize(3);
    }
}
