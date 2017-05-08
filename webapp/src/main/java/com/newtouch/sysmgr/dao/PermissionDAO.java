package com.newtouch.sysmgr.dao;

import com.newtouch.sysmgr.model.Permission;
import com.newtouch.sysmgr.model.Resource;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
@MapperScan
public interface PermissionDAO {

    List<Permission> findSelf(@Param("parentId") long parentId) throws Exception;
    List<Permission> findRootSelf() throws Exception;
    List<Permission> findAllSelf() throws Exception;
    long createSelf(Permission permission) throws Exception;
    int updateSelf(Permission permission) throws Exception;
    int modifySelfSite(Permission permission) throws Exception;
    int removeSelf(@Param("id") long id) throws Exception;

    List<Resource> findResource(@Param("permissionId") Long permissionId) throws Exception;
    List<Resource> findAllResource() throws Exception;
    long createResource(Resource resource) throws Exception;
    int removeResource(@Param("id") long id) throws Exception;

    List<Permission> findSelfByRole(@Param("roleId") long roleId) throws Exception;
    int connectRole(@Param("roleId") long roleId, @Param("permissionId") long permissionId) throws Exception;
    int disconnectRole(@Param("roleId") long roleId, @Param("permissionId") long permissionId) throws Exception;

}
