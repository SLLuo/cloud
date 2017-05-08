package demo;

/**
 * Created by Administrator on 2017/4/19.
 */
public class User {

    private String name;
    private Integer sex;

    public User() {
    }

    public User(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
