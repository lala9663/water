package com.meta.metaway.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private IProductRepository productRepository;

	@Spy // ProductService를 스파이 객체로 사용
	@InjectMocks
	private ProductService productService;

	private Product product;
	private List<MultipartFile> fileList;
	private MultipartFile file;

	@DisplayName("상품 파일 업로드")
	@Test
	void testProductFileInsert() {
		product = new Product();
		product.setFunction(Arrays.asList((short) 1)); // function 필드 초기화
		file = mock(MultipartFile.class);
		product.setContract(new ArrayList<Contract>());
		fileList = Arrays.asList(file);
		when(file.getOriginalFilename()).thenReturn("testFile.jpg");

		// productFileUpload 메소드 모의 처리
		doNothing().when(productService).productFileUpload(any(Product.class), anyList());

		String result = productService.productFileInsert(product, fileList);

		verify(productRepository).productInsert(any(Product.class));
		// 실제 파일 업로드 로직은 실행되지 않음
		verify(productService).productFileUpload(any(Product.class), anyList());

		assertEquals("상품 등록 성공", result);

	}
	@DisplayName("상품 이미지 파일 업데이트")
	@Test
	public void testUpdateproductFile() {
        product = new Product();
        product.setFunction(Arrays.asList((short)1)); // function 필드 초기화
        product.setContract(Arrays.asList(new Contract())); // contract 필드 초기화
        file = mock(MultipartFile.class);
        fileList = Arrays.asList(file);
        when(file.getOriginalFilename()).thenReturn("testFile.jpg");
        when(file.isEmpty()).thenReturn(false);
        
        long productId = 123L;
        product.setProductId(productId);

        when(productRepository.getProductContractList(productId)).thenReturn(Arrays.asList(new Contract()));
        when(productRepository.getProductKey(productId)).thenReturn(Arrays.asList("Key1", "Key2"));
        
        // productFileUpload 메소드 모의 처리
        doNothing().when(productService).productFileUpload(any(Product.class), anyList());

        String result = productService.UpdateproductFile(product, fileList);

        verify(productRepository).UpdateProductInfo(any(Product.class));
        verify(productRepository).DeleteProductFunction(productId);
        verify(productRepository).DeleteProductContract(productId);
        verify(productRepository).DeleteProductFile(productId);
        verify(productService).productFileUpload(any(Product.class), anyList());

        assertEquals("상품 업데이트 성공", result);
	}

	@Test
	public void testProductDelete() {
		long productId = 123L;

		// 서비스 메소드 호출
		productService.productDelete(productId);

		// 리포지토리 메소드 호출 검증
		verify(productRepository).productDelete(productId);
	}
	
	@DisplayName("대상 약정 키 반환")
	@Test
	public void testGetProductKey() {
		long productId = 123L;
		List<String> mockResponse = Arrays.asList("Key1", "Key2");
		when(productRepository.getProductKey(productId)).thenReturn(mockResponse);

		List<String> result = productService.getProductKey(productId);

		verify(productRepository).getProductKey(productId);
		assertEquals(mockResponse, result);
	}
	
	@DisplayName("대상 상품 정보 반환")
	@Test
	public void testGetTargetProductInfo() {
		long productId = 123L;
		Product mockProduct = new Product();
		mockProduct.setProductId(productId);
		mockProduct.setProductName("Test Product");
		mockProduct.setImageFile("imagePath");
		when(productRepository.getTargetProductInfo(productId)).thenReturn(mockProduct);
		when(productRepository.getProductKey(productId)).thenReturn(Arrays.asList("Function1", "Function2"));

		when(productService.productImageString("imagePath")).thenReturn("encodedString");

		Product result = productService.getTargetProductInfo(productId);

		verify(productRepository).getTargetProductInfo(productId);
		assertNotNull(result);
		assertEquals(productId, result.getProductId());
		assertEquals("Test Product", result.getProductName());
		// 실제 파일 경로 대신 모의 처리된 Base64 인코딩 문자열을 검증
		assertEquals("encodedString", result.getImageFile());
		assertEquals(Arrays.asList("Function1", "Function2"), result.getFunctionList());
		// 추가적인 결과 상태 검증
	}
	@DisplayName("전체 상품 리스트 반환")
	@Test
	public void testGetAllProductInfo() {
	     List<Product> mockProductList = Arrays.asList(new Product(), new Product());
	        when(productRepository.getAllProductInfo()).thenReturn(mockProductList);

	        List<Product> result = productService.getAllProductInfo();

	        verify(productRepository).getAllProductInfo();
	        assertEquals(mockProductList, result);
	}
	@DisplayName("약정리스트 반환")
	@Test
	public void testGetProductContractList() {
		long productId = 123L;
        List<Contract> mockContractList = Arrays.asList(new Contract(), new Contract());
        when(productRepository.getProductContractList(productId)).thenReturn(mockContractList);

        List<Contract> result = productService.getProductContractList(productId);

        verify(productRepository).getProductContractList(productId);
        assertEquals(mockContractList, result);
	}

}
