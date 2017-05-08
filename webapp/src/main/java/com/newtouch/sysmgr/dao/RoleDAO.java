package com.newtouch.sysmgr.dao;

import com.newtouch.sysmgr.model.Role;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
@MapperScan
public interface RoleDAO {

    List<Role> findSelf(@Param("title") String title) throws Exception;
    long createSelf(Role role) throws Exception;
    int modifySelf(Role role) throws Exception;
    int removeSelf(@Param("id") long id) throws Exception;

    List<Role> findSelfByPosition(@Param("positionId") long positionId) throws Exception;
    int connectRole(@Param("positionId") long positionId, @Param("roleId") long roleId, @Param("allowGrant") Boolean allowGrant) throws Exception;
    int disconnectRole(@Param("positionId") long positionId, @Param("roleId") long roleId) throws Exception;

}
