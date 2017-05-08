package com.newtouch.sysmgr.dao;

import com.newtouch.sysmgr.model.User;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
@MapperScan
public interface UserDAO {

    List<User> findSelf(@Param("enabled") Boolean enabled,
                        @Param("username") String username,
                        @Param("realName") String realName) throws Exception;
    User getSelf(@Param("id") long id) throws Exception;
    long createSelf(User user) throws Exception;
    int modifySelf(User user) throws Exception;
    int modifyPassword(User user) throws Exception;
    int removeSelf(@Param("id") long id) throws Exception;
}
