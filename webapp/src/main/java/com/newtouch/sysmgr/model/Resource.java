package com.newtouch.sysmgr.model;

import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/31.
 */
@Alias("resource")
public class Resource {

    private Long id;
    private String source;
    private Permission permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
