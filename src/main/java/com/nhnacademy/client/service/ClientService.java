package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.request.ClientOAuthRegisterRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterPhoneNumberRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.response.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    /**
     * 회원 정보를 받아 회원가입을 수행하는 함수입니다.
     *
     * @author gihwanJang
     * @param registerInfo 회원정보를 가지는 DTO
     * @return 등록 성공시 회원가입 일시, 회원 이메일을 실패시 ClientEmailDupllicates 예외를 반환합니다..
     */
    ClientRegisterResponseDto register(ClientRegisterRequestDto registerInfo);

    /**
     * Oauth 회원 정보를 받아 회원가입을 수행하는 함수입니다.
     *
     * @author gihwanJang
     * @param clientOAuthRegisterRequestDto
     * @return 성공 여부를 반환합니다.
     */
    String oauthRegister(ClientOAuthRegisterRequestDto clientOAuthRegisterRequestDto);

    /**
     * 이메일을 받아 해당 유저의 로그인시 필요한 정보를 반환하는 함수입니다.
     *
     * @author gihwanJang
     * @param email 유저를 식별하는 인자입니다.
     * @return 이메일값을 가지는 유저가 존재한다면 해당 유저의 정보를, 존재하지 않을 시 NotFoundClient 예외를 반환합니다.
     */
    ClientLoginResponseDto login(String email);

    /**
     * 마이페이지에서 개인정보를 불러올 시 사용되는 api 함수로 이메일을 받아 해당 유저의 개인정보를 반환하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @return 이메일값을 가지는 유저가 존재한다면 유저의 개인정보를, 존재하지 않을 시 NotFoundClient 예외를 반환합니다.
     */
    ClientPrivacyResponseDto privacy(Long id);

    /**
     * 쿠폰 발급시 사용되는 api 함수로 페이지와, 한 페이지의 사이즈를 인자로 받아 모든 유저정보를 반환하는 함수입니다. (관리자 권한 필요)
     *
     * @author gihwanJang
     * @param page 요구하는 페이지의 값입니다.
     * @param size 한페이지 내의 보여질 정보의 갯수입니다.
     * @return 해당하는 페이지의 유저정보 페이지를 반환합니다.
     */
    Page<ClientCouponPaymentResponseDto> couponPayment(int page, int size);

    /**
     * 마이페이지에서 사용되는 api 함수 이메일을 받아 해당 유저의 배송지 리스트를 반환합니다. (권한 필요)
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @return 해당하는 유저가 존재한다면 배송지 리스트를, 존재하지 않는다면 NotFoundClient 예외를 반환합니다.
     */
    List<ClientDeliveryAddressResponseDto> deliveryAddress(Long id);

    /**
     * 마이페이지에서 사용되는 api함수 이메일을 받아 해당 유저의 연락처 리스트를 반환합니다. (권한 필요)
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @return 해당하는 유저가 존재한다면 연락처 리스트를, 존재하지 않는다면 NotFoundClient 예외를 반환합니다.
     */
    List<ClientPhoneNumberResponseDto> getPhoneNumbers(Long id);

    /**
     * 주문시 사용되는 api 함수로 이메일을 받아 해당 유저의 정보 반환하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @return 해당하는 유저가 존재한다면 유저 정보를, 존재하지 않는다면 NotFoundClient 예외를 반환합니다.
     */
    ClientOrderResponseDto order(Long id);

    /**
     * 마이페이지의 배송지 관리시 사용되는 api함수로 email과 배송지를 인자로 받아 해당 유저의 배송지로 등록하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param clientRegisterAddressDto 등록 할 주소의 정보입니다.
     * @param id 유저를 식별하는 인자입니다.
     * @return 해당하는 유저가 존재하고 성공시, 존재하지 않는다면 NotFoundClient 예외를 반환합니다.
     */
    String registerAddress(ClientRegisterAddressRequestDto clientRegisterAddressDto, Long id);

    /**
     * 마이페이지의 배송지 관리시 사용되는 api함수로 배송지 아이디를 인지로 받아 해당 주소를 삭제하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param clientPhoneNumberResponseDto 등록할 번호의 정보입니다.
     * @param id 유저를 식별하는 인자입니다.
     * @return 해당 유저가 존재하고 성공시 "Success" 실패시 NotFoundClient 예외
     */
    String registerPhoneNumber(ClientRegisterPhoneNumberRequestDto clientPhoneNumberResponseDto, Long id);

    /**
     * 마이페이지의 배송지 관리시 사용되는 api함수로 배송지 아이디를 인지로 받아 해당 주소를 삭제하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param addressId 주소를 식별하는 인자입니다.
     * @return 성공시 "Success" 실패시 NotFoundClient 예외
     */
    String deleteAddress(Long addressId);

    /**
     * 마이페이지의 배송지 관리시 사용되는 api함수로 배송지 아이디를 인지로 받아 해당 주소를 삭제하는 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param phoneNumberId 전화번호를 식별하는 인자입니다.
     * @return 성공시 "Success" 실패시 NotFoundClient 예외
     */
    String deletePhoneNumber(Long phoneNumberId);

    /**
     *  이메일과 비밀번호르 인자로 받아 해당하는 유저를 삭제하는 api 함수입니다. (권한 필요)
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @param password 삭제시 확인 수단으로 Header 인자
     * @return 성공시 "Success" 실패시 NotFoundClient, ClientAuthenticationFailed 예외
     */
    String deleteClient(Long id, String password);

    /**
     * 이름과 생일을 인자로 받아 해당 유저를 업데이트 하는 api 함수입니다.
     *
     * @author gihwanJang
     * @param id 유저를 식별하는 인자입니다.
     * @param name 업데이트할 닉네임 입니다.
     * @param birth 업데이트할 생일 입니다.
     * @return 성공시 "Success" 실패시 NotFoundClient 예외
     */
    String updateClient(Long id, String name, LocalDate birth);

    /**
     * 이메일을 인자로 받아 휴면 계정을 복구하는 api입니다.
     *
     * @param email 유저를 식별하는 인자입니다.
     * @param token 요청 검증 값입니다.
     * @return 성공시 "Success" 실패시 NotFoundClient, BadRequest 예외
     */
    String recveryClinet(String email, String token);

    /**
     * 이메일을 인자로 받아 휴면 계정을 복구하는 api입니다.
     *
     *
     * @param email 유저를 식별하는 인자입니다.
     * @return 성공시 "Success" 실패시 NotFoundClient 예외
     */
    String recveryOauthClinet(String email);

    /**
     * 이메일과 비밀번호를 인자로 받아 비밀번호를 변경하는 api 함수입니다.
     *
     * @param email 유저를 식별하는 인자입니다.
     * @param password 수정할 비밀번호 입니다.
     * @param token 요청 검증 값입니다..=
     * @return 성공시 "Success" 실패시 NotFoundClient, BadRequest 예외
     */
    String changePasswordClient(String email, String password, String token);

    /**
     * 로그인 시간을 업데이트하는 함수입니다.
     *
     * @param message 유저의 id와 로그인 시간입니다.
     */
    void receiveMessage(String message);

    /**
     * 3달간 로그인 하지않은 유저를 매일자정 휴면처리 시키는 함수입니다.
     */
    void updateInactiveClients();
}
