package com.newtouch.sysmgr.service;

import com.newtouch.sysmgr.dao.PermissionDAO;
import com.newtouch.sysmgr.dao.RoleDAO;
import com.newtouch.sysmgr.dao.UserDAO;
import com.newtouch.commons.shiro.ShiroPrincipal;
import com.newtouch.commons.shiro.ShiroService;
import com.newtouch.sysmgr.model.*;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/3/31.
 */
@Service("shiroService")
public class ShiroServiceImpl extends ShiroService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PermissionDAO permissionDAO;

    public ShiroServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PermissionDAO permissionDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.permissionDAO = permissionDAO;
    }

    @Override
    public ShiroPrincipal findPrincipalByUsername(String username) throws Exception {
        List<User> list = userDAO.findSelf(true, username, null);
        if (list.isEmpty()) return null;
        User user = list.get(0);
        ShiroPrincipal principal = new ShiroPrincipal();
        principal.setId(user.getId());
        principal.setUsername(user.getUsername());
        principal.setPassword(user.getPassword());
        principal.setSalt(user.getSalt());
        principal.setEnabled(user.getEnabled());
        principal.setRealName(user.getRealName());
        Position position = user.getPosition();
        if (position == null) {
            throw new UnknownAccountException("用户暂无职位");
        }
        principal.setPositionId(position.getId());
        principal.setPositionName(position.getName());
        return principal;
    }

    @Override
    public void initRoleAndPermission(ShiroPrincipal principal) throws Exception {
        List<Role> roles = roleDAO.findSelfByPosition(principal.getPositionId());
        Set<String> roleNames = new HashSet<>();
        List<Permission> permissions;
        Set<String> permissionNames = new HashSet<>();
        for (Role role : roles) {
            roleNames.add(role.getName());
            permissions = permissionDAO.findSelfByRole(role.getId());
            for (Permission permission : permissions) {
                permissionNames.add(permission.getName());
            }
        }
        principal.setRoles(roleNames);
        principal.setPermissions(permissionNames);
    }

    @Override
    public Map<String, Set<String>> getAllResourcePermissions() throws Exception {
        Map<String, Set<String>> permissionsMap = new HashMap<>();
        List<Resource> resources = permissionDAO.findAllResource();
        Set<String> permissions;
        for (Resource resource : resources) {
            permissions = permissionsMap.get(resource.getSource());
            if (permissions == null) {
                permissions = new HashSet<>();
                permissionsMap.put(resource.getSource(), permissions);
            }
            permissions.add(resource.getPermission().getName());
        }
        return permissionsMap;
    }
}
