package com.meta.metaway.user.service;

import com.meta.metaway.user.model.Staff;

public interface IStaffService {
    boolean isCodiOrDriver(String account);
    void createWorkPlace(String account, String workPlace);
    void updateWorkPlace(String account, Staff staff);
    Long getUserIdByAccount(String account);

}
