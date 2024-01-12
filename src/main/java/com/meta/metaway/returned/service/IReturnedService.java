package com.meta.metaway.returned.service;


import com.meta.metaway.returned.model.Returned;

public interface IReturnedService {
	void InsertReturnTable(Returned returned);
	void CancelproductReturn(Returned returned);
}
