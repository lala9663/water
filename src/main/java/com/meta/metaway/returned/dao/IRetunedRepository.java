package com.meta.metaway.returned.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.returned.model.Returned;

@Repository
@Mapper
public interface IRetunedRepository {
	public void InsertReturnTable(Returned returned);
	void updateReturnTable(Returned returned);
	void CancelproductReturn(Returned returned);
	long getNextMaxReturnId();
}
