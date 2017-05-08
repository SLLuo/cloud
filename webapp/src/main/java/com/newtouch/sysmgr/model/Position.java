package com.newtouch.sysmgr.model;

import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/31.
 */
@Alias("position")
public class Position {

    private Long id;
    private String name;
    private Branch branch;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
