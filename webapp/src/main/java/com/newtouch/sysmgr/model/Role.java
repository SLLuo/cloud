package com.newtouch.sysmgr.model;

import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/31.
 */
@Alias("role")
public class Role {

    private Long id;
    private String name;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
