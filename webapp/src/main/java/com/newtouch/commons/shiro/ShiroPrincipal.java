package com.newtouch.commons.shiro;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义认证主体
 *
 * @author yuqs
 * @version 1.0
 */
public class ShiroPrincipal implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1428196040744555722L;
    //用户对象
    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean enabled;
    private String realName;
    private Long positionId;
    private String positionName;
    //这里会导致修改权限时，需要重新登录方可有效
    private boolean init = false;
    //用户角色列表
    private Set<String> roles = new HashSet<String>();
    //用户权限列表
    private Set<String> permissions = new HashSet<String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * <shiro:principal/>标签显示中文名称
     */
    @Override
    public String toString() {
        return this.realName;
    }
}
