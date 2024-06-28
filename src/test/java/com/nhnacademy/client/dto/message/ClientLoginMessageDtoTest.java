package com.nhnacademy.client.dto.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClientLoginMessageDtoTest {

    private ClientLoginMessageDto clientLoginMessageDto;
    private Long clientId = 1L;
    private LocalDateTime lastLoginDate = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        clientLoginMessageDto = new ClientLoginMessageDto(clientId, lastLoginDate);
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientLoginMessageDto.getClientId()).isEqualTo(clientId);
        assertThat(clientLoginMessageDto.getLastLoginDate()).isEqualTo(lastLoginDate);

        // Test setter
        Long newClientId = 2L;
        LocalDateTime newLastLoginDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        clientLoginMessageDto.setClientId(newClientId);
        clientLoginMessageDto.setLastLoginDate(newLastLoginDate);

        assertThat(clientLoginMessageDto.getClientId()).isEqualTo(newClientId);
        assertThat(clientLoginMessageDto.getLastLoginDate()).isEqualTo(newLastLoginDate);
    }

    @Test
    void testNoArgsConstructor() {
        ClientLoginMessageDto dto = new ClientLoginMessageDto();
        assertThat(dto.getClientId()).isNull();
        assertThat(dto.getLastLoginDate()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ClientLoginMessageDto dto = new ClientLoginMessageDto(clientId, lastLoginDate);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getLastLoginDate()).isEqualTo(lastLoginDate);
    }

    @Test
    void testSerializable() throws IOException, ClassNotFoundException {
        // Serialize the object to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(clientLoginMessageDto);
        objectOutputStream.flush();
        byte[] serializedObject = byteArrayOutputStream.toByteArray();

        // Deserialize the byte array to an object
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ClientLoginMessageDto deserializedObject = (ClientLoginMessageDto) objectInputStream.readObject();

        // Verify that the deserialized object is equal to the original object
        assertThat(deserializedObject).isNotNull();
        assertThat(deserializedObject.getClientId()).isEqualTo(clientLoginMessageDto.getClientId());
        assertThat(deserializedObject.getLastLoginDate()).isEqualTo(clientLoginMessageDto.getLastLoginDate());
    }
}
