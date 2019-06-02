package com.xiaohui.service;

import com.xiaohui.entity.InfoUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: xiaohui
 * @Description: RPC远程接口
 * @Date: 2019/6/2 8:22
 */
public interface InfoUserService {

    List<InfoUser> insertInfoUser(InfoUser infoUser);

    InfoUser getInfoUserById(String id);

    void deleteInfoUserById(String id);

    String getNameById(String id);

    Map<String,InfoUser> getAllUser();
}
