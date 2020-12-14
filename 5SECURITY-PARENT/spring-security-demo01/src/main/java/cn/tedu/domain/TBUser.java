package cn.tedu.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TBUser implements Serializable, UserDetails {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private Date created;
    private Date updated;
    List<Permission> authorities;

    public void setAuthorities(List<Permission> authorities) {
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {

        this.username = username;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {

        return password;

    }



    public void setPassword(String password) {

        this.password = password;

    }



    public String getPhone() {

        return phone;

    }



    public void setPhone(String phone) {

        this.phone = phone;

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

    @Override
    public boolean equals(Object o) {
        //username
        if(o instanceof TBUser){
            TBUser user=(TBUser)o;
            return user.getUsername().equals(this.username);
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
