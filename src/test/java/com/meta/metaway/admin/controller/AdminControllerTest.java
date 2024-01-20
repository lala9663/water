package com.meta.metaway.admin.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.admin.service.IAdminReturnService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
	@Mock
	private IAdminReturnService adminReturnService;
	@InjectMocks
	private AdminController adminController;

	@Test
	public void testOrderList() {
		throw new RuntimeException("not yet implemented");
	}

}
