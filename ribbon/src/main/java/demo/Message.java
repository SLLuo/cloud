package demo;

/**
 * Created by Administrator on 2017/4/19.
 */
public class Message {

    private Integer state;
    private Role role;
    private String error;

    public Message() {
    }

    public Message(Integer state, Role role, String error) {
        this.state = state;
        this.role = role;
        this.error = error;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
