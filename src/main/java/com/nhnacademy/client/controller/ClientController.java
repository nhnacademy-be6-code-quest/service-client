package com.nhnacademy.client.controller;

import com.nhnacademy.client.dto.request.*;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.exception.ClientAuthenticationFailedException;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client", description = "유저 관련 API")
public interface ClientController {
    @Operation(
            summary = "회원가입",
            description = "Auth - 사용자를 등록",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 시간 및 회원 이메일 반환"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이메일 중복시 반환"
                    )
            }
    )
    @PostMapping("/api/client")
    ResponseEntity<ClientRegisterResponseDto> createClient(
            @Parameter(description = "회원가입을 시도한 유저의 정보")
            @Valid @RequestBody ClientRegisterRequestDto clientRegisterRequestDto
    );

    @Operation(
            summary = "OAuth 회원가입",
            description = "OAuth - OAuth 사용자를 등록",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
            }
    )
    @PostMapping("/api/oauth/client")
    ResponseEntity<String> createOauthClient(@RequestBody ClientOAuthRegisterRequestDto clientOAuthRegisterRequestDto);

    @Operation(
            summary = "로그인 유저 조회",
            description = "Auth - 로그인을 시도한 사용자를 검색",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "검색된 회원 정보 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @GetMapping("/api/client/login")
    ResponseEntity<ClientLoginResponseDto> login(
            @Parameter(description = "회원가입을 시도한 유저의 정보")
            @RequestParam String email);

    @Operation(
            summary = "유저정보 조회",
            description = "MyPage - 유저정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "검색된 회원 정보 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @GetMapping("/api/client")
    ResponseEntity<ClientPrivacyResponseDto> getPrivacy(
            @Parameter(description = "id 해더")
            @RequestHeader HttpHeaders httpHeaders
    );

    @Operation(
            summary = "유저 페이지 조회",
            description = "AdminPage - 쿠폰발급시 유저 페이지 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 페이지 반환"
                    ),
            }
    )
    @GetMapping("/api/client/coupon-payment")
    ResponseEntity<Page<ClientCouponPaymentResponseDto>> getCouponPayments(
            @Parameter(description = "페이지")
            @RequestParam(name = "page") int page,
            @Parameter(description = "한 페이지에 포함될 객체의 수")
            @RequestParam(name = "size") int size);

    @Operation(
            summary = "유저 배송지 조회",
            description = "MyPage - 유저 배송지 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 배송지 리스트 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @GetMapping("/api/client/address")
    ResponseEntity<List<ClientDeliveryAddressResponseDto>> getDeliveryAddresses(
            @Parameter(description = "id 해더")
            @RequestHeader HttpHeaders httpHeaders
    );

    @Operation(
            summary = "주문자 정보 조회",
            description = "Order - 유저 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문자 정보 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @GetMapping("/api/client/order")
    ResponseEntity<ClientOrderResponseDto> getOrders(
            @Parameter(description = "id  해더")
            @RequestHeader HttpHeaders httpHeaders
    );

    @Operation(
            summary = "배송지 등록",
            description = "MyPage - 유저 배송지 등록",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @PostMapping("/api/client/address")
    ResponseEntity<String> registerAddress(
            @Parameter(description = "id 해더")
            @RequestHeader HttpHeaders httpHeaders,
            @Parameter(description = "배송지 정보")
            @RequestBody ClientRegisterAddressRequestDto clientRegisterAddressDto
    );

    @Operation(
            summary = "배송지 삭제",
            description = "MyPage - 유저 배송지 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @DeleteMapping("/api/client/address")
    ResponseEntity<String> deleteAddress(
            @Parameter(description = "id 해더")
            @RequestParam Long addressId
    );

    @Operation(
            summary = "회원 탈퇴",
            description = "MyPage - 유저 비활성화",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @DeleteMapping("/api/client")
    ResponseEntity<String> deleteClient(
            @Parameter(description = "id 해더, password 해더")
            @RequestHeader HttpHeaders httpHeaders
    );

    @Operation(
            summary = "회원 연락처 검색",
            description = "MyPage - 회원의 연락처 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원의 연락처 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @GetMapping("/api/client/phone")
    ResponseEntity<List<ClientPhoneNumberResponseDto>> getPhoneNumbers(
            @Parameter(description = "id 해더, password 해더")
            @RequestHeader HttpHeaders httpHeaders
    );

    @Operation(
            summary = "회원 연락처 추가",
            description = "MyPage - 회원의 연락처 추가",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @PostMapping("/api/client/phone")
    ResponseEntity<String> registerPhoneNumber(
            @Parameter(description = "id 해더, password 해더")
            @RequestHeader HttpHeaders httpHeaders,
            @Parameter(description = "회원 전화번호 정보")
            @RequestBody ClientRegisterPhoneNumberRequestDto clientRegisterPhoneNumberDto
    );

    @Operation(
            summary = "회원 연락처 삭제",
            description = "MyPage - 회원의 연락처 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @DeleteMapping("/api/client/phone")
    ResponseEntity<String> deletePhoneNumber(
            @Parameter(description = "연락처 id")
            @RequestParam(name = "phoneNumberId") Long phoneNumberId
    );

    @Operation(
            summary = "회원 업데이트",
            description = "MyPage - 회원의 정보 업데이트",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공 여부 반환"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "검색된 유저가 없을 시 반환"
                    )
            }
    )
    @PutMapping("/api/client")
    ResponseEntity<String> updateClient(
            @Parameter(description = "id 해더, password 해더")
            @RequestHeader HttpHeaders httpHeaders,
            @Parameter(description = "수정할 유저 개인정보")
            @RequestBody ClientUpdatePrivacyRequestDto clientUpdatePrivacyRequestDto
            );

    ResponseEntity<ClientRegisterResponseDto> handleException(ClientEmailDuplicatesException e);

    ResponseEntity<ClientRegisterResponseDto> handleException(NotFoundClientException e);

    ResponseEntity<ClientLoginResponseDto> handleException(ClientAuthenticationFailedException e);
}
