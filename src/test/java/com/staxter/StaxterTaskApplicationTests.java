package com.staxter;

import com.staxter.Controller.UserController;
import com.staxter.domain.*;
import com.staxter.exception.DuplicateRecordException;
import com.staxter.exception.IncorrectUserNamePasswordException;
import com.staxter.exception.UserCreationException;
import com.staxter.util.ResponseUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.staxter.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class StaxterTaskApplicationTests {

	@Autowired
	UserController userController;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Test
	public void registerUser_success() {
		System.out.println("inside register");
		RegisterUserRequest registerUserRequest=newRegisterUserRequest();
		ResponseEntity<RegisterUserResponse> newUser = userController.registerUser(registerUserRequest);
		Assertions.assertEquals(HttpStatus.OK, newUser.getStatusCode());
		assertNotNull(newUser);
		assertEquals(registerUserRequest.getUserName(),newUser.getBody().getUserName());
		assertEquals(registerUserRequest.getFirstName(),newUser.getBody().getFirstName());
		assertEquals(registerUserRequest.getLastName(),newUser.getBody().getLastName());
	}

	@Test
	public void register_WithExistingUser() {
		RegisterUserRequest registerUserRequest=registerWithExistingUserReq();
		ResponseEntity<RegisterUserResponse> newUser = userController.registerUser(registerUserRequest);
		assertThrows(DuplicateRecordException.class,()->userController.registerUser(registerUserRequest));
	}

	@Test
	public void register_WithoutUser() {
		RegisterUserRequest registerUserRequest=registerWithoutUserReq();
		assertThrows(UserCreationException.class,()->userController.registerUser(registerUserRequest));
	}

	@Test
	public void register_WithoutPassword() {
		RegisterUserRequest registerUserRequest=registerWithoutPasswordReq();
		assertThrows(UserCreationException.class,()->userController.registerUser(registerUserRequest));
	}


	@Test
	public void login_Successfully() {
		RegisterUserRequest registerUserRequest=loginUserRequest();
		ResponseEntity<RegisterUserResponse> newUserResp = userController.registerUser(registerUserRequest);
		Assertions.assertEquals(HttpStatus.OK, newUserResp.getStatusCode());
		assertNotNull(newUserResp);
		ResponseEntity<GenericResponse> loginUserResp = userController.loginByUser(new LoginUser(registerUserRequest.getUserName(),registerUserRequest.getPassword()));
		Assertions.assertEquals(HttpStatus.OK, loginUserResp.getStatusCode());
		assertNotNull(loginUserResp);
		Assertions.assertEquals(ResponseUtil.USER_LOGGED_IN_SUCCESSFULLY.getValue(),loginUserResp.getBody().getCode());
		Assertions.assertEquals(ResponseUtil.USER_LOGGED_IN_SUCCESSFULLY_DESCRIPTION.getValue(),loginUserResp.getBody().getDescription());
	}

	@Test
	public void login_InvalidUserName() {
		RegisterUserRequest registerUserRequest=loginInvalidUserNameReq();
		ResponseEntity<RegisterUserResponse> newUserResp = userController.registerUser(registerUserRequest);
		Assertions.assertEquals(HttpStatus.OK, newUserResp.getStatusCode());
		assertNotNull(newUserResp);
		assertThrows(BadCredentialsException.class,()->userController.loginByUser(new LoginUser(registerUserRequest.getUserName()+"Invalid",registerUserRequest.getPassword())));
	}

	@Test
	public void login_WithoutUserName_Password() {
		assertThrows(IncorrectUserNamePasswordException.class,()->userController.loginByUser(new LoginUser("","")));
	}

	@Test
	public void login_InvalidPassword() {
		RegisterUserRequest registerUserRequest=loginInvalidPasswordReq();
		ResponseEntity<RegisterUserResponse> newUserResp = userController.registerUser(registerUserRequest);
		Assertions.assertEquals(HttpStatus.OK, newUserResp.getStatusCode());
		assertNotNull(newUserResp);
		assertThrows(BadCredentialsException.class,()->userController.loginByUser(new LoginUser(registerUserRequest.getUserName(),registerUserRequest.getPassword()+"Invalid")));
	}



	@Test
	public void login_WithNonExistingUser() {
		assertThrows(BadCredentialsException.class,()->userController.loginByUser(new LoginUser(loginWithNonExistingUser().getUserName(), loginWithNonExistingUser().getPassword())));
	}

	private RegisterUserRequest newRegisterUserRequest() {
		RegisterUserRequest registerUser = new RegisterUserRequest();
		registerUser.setFirstName("kirti1");
		registerUser.setLastName("test1");
		registerUser.setUserName("ki1");
		registerUser.setPassword("test@1234");
		return registerUser;
	}
	private RegisterUserRequest loginUserRequest() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("A");
		loginUserReq.setLastName("B");
		loginUserReq.setUserName("ab");
		loginUserReq.setPassword("test@1234");
		return loginUserReq;
	}
	private RegisterUserRequest loginInvalidUserNameReq() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("AA");
		loginUserReq.setLastName("BS");
		loginUserReq.setUserName("abc");
		loginUserReq.setPassword("test@1234");
		return loginUserReq;
	}

	private RegisterUserRequest loginInvalidPasswordReq() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("AAA");
		loginUserReq.setLastName("BSS");
		loginUserReq.setUserName("abcd");
		loginUserReq.setPassword("test@1234");
		return loginUserReq;
	}

	private RegisterUserRequest registerWithExistingUserReq() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("AAAA");
		loginUserReq.setLastName("BSSS");
		loginUserReq.setUserName("abcde");
		loginUserReq.setPassword("test@1234");
		return loginUserReq;
	}
	private RegisterUserRequest registerWithoutUserReq() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("AAAA");
		loginUserReq.setLastName("BSSS");
		loginUserReq.setUserName("");
		loginUserReq.setPassword("test@1234");
		return loginUserReq;
	}

	private RegisterUserRequest registerWithoutPasswordReq() {
		RegisterUserRequest loginUserReq = new RegisterUserRequest();
		loginUserReq.setFirstName("AAAA");
		loginUserReq.setLastName("BSSS");
		loginUserReq.setUserName("RESA");
		loginUserReq.setPassword("");
		return loginUserReq;
	}

	private RegisterUserRequest loginWithNonExistingUser() {
		RegisterUserRequest loginWithNonExistingUserReq = new RegisterUserRequest();
		loginWithNonExistingUserReq.setFirstName("AAAAA");
		loginWithNonExistingUserReq.setLastName("BSSSS");
		loginWithNonExistingUserReq.setUserName("abcdef");
		loginWithNonExistingUserReq.setPassword("test@12345");
		return loginWithNonExistingUserReq;
	}

}
