package com.newtouch.sysmgr.model;

import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/31.
 */
@Alias("branch")
public class Branch {

    private String code;
    private String name;
    private String parent;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
