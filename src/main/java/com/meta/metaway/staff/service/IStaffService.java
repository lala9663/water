package com.meta.metaway.staff.service;

import com.meta.metaway.staff.model.Staff;

public interface IStaffService {
    boolean isCodiOrDriver(String account);
    void createWorkPlace(String account, String workPlace);
    void updateWorkPlace(String account, Staff staff);
    Long getUserIdByAccount(String account);

}
