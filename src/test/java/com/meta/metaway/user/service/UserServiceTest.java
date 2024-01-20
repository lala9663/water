package com.meta.metaway.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.meta.metaway.order.model.Order;
import com.meta.metaway.order.model.OrderDetail;
import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.user.dao.IBasketRepository;
import com.meta.metaway.user.dao.IUserRepository;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private IBasketRepository basketRepository;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private IProductRepository productRepository;

	@InjectMocks
	private UserService userService;

	@Mock
	private IUserRepository userRepository;

	@DisplayName("계정 검증")
	@Test
	public void testCheckIfUserExists() {
		String account = "testAccount";
		when(userRepository.existsByAccount(account)).thenReturn(true);

		boolean result = userService.checkIfUserExists(account);

		verify(userRepository).existsByAccount(account);
		assertTrue(result);
	}

	@DisplayName("새 사용자 등록")
	@Test
	public void testJoinProcess() {
		JoinDTO joinDTO = new JoinDTO();
		joinDTO.setAccount("newUser");
		joinDTO.setPassword("password");
		// ... 다른 필드 설정

		when(userRepository.existsByAccount(joinDTO.getAccount())).thenReturn(false);
		when(userRepository.selectUserMaxNo()).thenReturn(1L);

		userService.joinProcess(joinDTO);

		verify(userRepository).existsByAccount(joinDTO.getAccount());
		verify(userRepository).selectUserMaxNo();
		verify(userRepository).insertUser(any(User.class));
		verify(userRepository).insertUserRole(any(User.class));
	}

	@DisplayName("사용자 정보 조회")
	@Test
	public void testGetUserByUsername() {
		String username = "testUser";
		User mockUser = new User();
		mockUser.setAccount(username);
		when(userRepository.findByInfo(username)).thenReturn(mockUser);

		User result = userService.getUserByUsername(username);

		verify(userRepository).findByInfo(username);
		assertEquals(username, result.getAccount());
	}

	@DisplayName("사용자 정보 업데이트")
	@Test
	public void testUpdateUser() {
		String account = "user";
		User user = new User();
		user.setAccount(account);

		when(userRepository.getUserIdByAccount(account)).thenReturn(1L);
		when(userRepository.findByInfo(account)).thenReturn(user);

		User updatedUser = userService.updateUser(account, user);

		verify(userRepository).getUserIdByAccount(account);
		verify(userRepository).updateUser(user);
		assertNotNull(updatedUser);
	}

	@DisplayName("비밀번호 변경")
	@Test
	public void testUpdateUserPassword() {
		String account = "user";
		User user = new User();
		user.setAccount(account);
		user.setPassword("newPassword");

		when(userRepository.findByInfo(account)).thenReturn(user);

		User updatedUser = userService.updateUserPassword(account, user);

		verify(userRepository).findByInfo(account);
		verify(userRepository).updateUser(user);
		assertNotNull(updatedUser);
	}

	@DisplayName("장바구니 조회")
	@Test
	public void testGetBasketItemsByUserId() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userRepository, basketRepository, null, null);
		userService.productRepository = productRepository;
		long userId = 1L;

		// Mocking: basketRepository.getBasketItemsByUserId() 호출 시 필요한 Product1,
		// Product2 객체가 포함된 리스트 반환
		Product product1 = new Product(); // TODO: Product 객체 초기화
		Product product2 = new Product(); // TODO: Product 객체 초기화
		when(basketRepository.getBasketItemsByUserId(userId)).thenReturn(Arrays.asList(product1, product2));

		// Act
		List<Product> result = userService.getBasketItemsByUserId(userId);

		// Assert: 반환된 결과를 검증 (예: 리스트의 크기 확인)
		assertEquals(2, result.size());

	}

	@DisplayName("장바구니 넣기")
	@Test
	public void testAddProductToBasket() throws Exception {
		Basket basket = new Basket();
		basket.setUserId(1L);
		basket.setProductId(1L);

		userService.addProductToBasket(basket);

		verify(basketRepository).addProductToBasket(basket);
	}

	@DisplayName("장바구니 제거")
	@Test
	public void testRemoveProductFromBasket() throws Exception {
		Basket basket = new Basket();
		basket.setUserId(1L);
		basket.setProductId(1L);

		userService.removeProductFromBasket(basket);

		verify(basketRepository).removeProductFromBasket(basket);
	}

	@DisplayName("비밀번호 일치 확인")
	@Test
	public void testCheckPassword() {
		Long userId = 1L;
		String enteredPassword = "password";
		String storedPassword = "encryptedPassword";

		when(userRepository.findPasswordById(userId)).thenReturn(storedPassword);
		when(passwordEncoder.matches(enteredPassword, storedPassword)).thenReturn(true);

		boolean result = userService.checkPassword(userId, enteredPassword);

		verify(userRepository).findPasswordById(userId);
		verify(passwordEncoder).matches(enteredPassword, storedPassword);
		assertTrue(result);
	}

	@DisplayName("유저 삭제")
	@Test
	public void testDeleteUserById() {
		Long userId = 1L;

		userService.deleteUserById(userId);

		verify(userRepository).deleteUserById(userId);
	}

	@DisplayName("주문목록 조회")
	@Test
	public void testGetOrdersByUserId() {
		Long userId = 1L;
		when(userRepository.getOrderByUserId(userId)).thenReturn(Arrays.asList(new Order()));

		List<Order> orders = userService.getOrdersByUserId(userId);

		verify(userRepository).getOrderByUserId(userId);
		assertNotNull(orders);
	}

	@DisplayName("주문 상세 조회")
	@Test
	public void testGetOrderDetailByUserId() {
		Long userId = 1L;
		when(userRepository.getOrderDetailByUserId(userId)).thenReturn(Arrays.asList(new OrderDetail()));

		List<OrderDetail> orderDetails = userService.getOrderDetailByUserId(userId);

		verify(userRepository).getOrderDetailByUserId(userId);
		assertNotNull(orderDetails);
	}

	@DisplayName("주문 조회")
	@Test
	public void testGetUserMyOrder() {
		Long userId = 1L;
		when(userRepository.getOrderByUserId(userId)).thenReturn(Arrays.asList(new Order()));

		List<Order> orders = userService.getUserMyOrder(userId);

		verify(userRepository).getOrderByUserId(userId);
		// 추가적인 검증 로직이 필요할 수 있음
		assertNotNull(orders);
	}

	@DisplayName("특정 주문 조회")
	@Test
	public void testGetUserMyOrderDetail() {
		Order order = new Order();
		order.setOrderId(1L);
		when(userRepository.getOrderByOrderId(order.getOrderId())).thenReturn(order);

		Order resultOrder = userService.getUserMyOrderDetail(order);

		verify(userRepository).getOrderByOrderId(order.getOrderId());
		// 추가적인 검증 로직이 필요할 수 있음
		assertNotNull(resultOrder);
	}

	@DisplayName("아이디 검증")
	@Test
	public void testIsAccountAvailable() {
		String account = "testAccount";
		when(userRepository.checkAccount(account)).thenReturn(1);

		int result = userService.isAccountAvailable(account);

		verify(userRepository).checkAccount(account);
		assertEquals(1, result);
	}

}
