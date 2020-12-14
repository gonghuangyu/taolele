package cn.tedu.domain;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;
public class Permission implements Serializable, GrantedAuthority {
    private Long id;
    private Long userId;
    private String name;
    private String authority;
    private Date created;
    private Date updated;
    public Long getId() {
        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public Long getUserId() {

        return userId;

    }

    public void setUserId(Long userId) {

        this.userId = userId;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getAuthority() {

        return authority;

    }

    public void setAuthority(String authority) {

        this.authority = authority;

    }

    public Date getCreated() {

        return created;

    }

    public void setCreated(Date created) {

        this.created = created;

    }

    public Date getUpdated() {

        return updated;

    }

    public void setUpdated(Date updated) {

        this.updated = updated;

    }

}
