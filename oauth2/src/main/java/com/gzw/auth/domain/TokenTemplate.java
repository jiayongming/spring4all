package com.gzw.auth.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by qzj on 2018/3/28
 */
@Entity
@Table(name = "tokentemplate")
public class TokenTemplate {
    @Id
    private String principal;
    private String tokenType;
    private Date expiration;
    private String access;
    private String auth_to_access;
    private String uname_to_access;
    private String client_id_to_access;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAuth_to_access() {
        return auth_to_access;
    }

    public void setAuth_to_access(String auth_to_access) {
        this.auth_to_access = auth_to_access;
    }

    public String getUname_to_access() {
        return uname_to_access;
    }

    public void setUname_to_access(String uname_to_access) {
        this.uname_to_access = uname_to_access;
    }

    public String getClient_id_to_access() {
        return client_id_to_access;
    }

    public void setClient_id_to_access(String client_id_to_access) {
        this.client_id_to_access = client_id_to_access;
    }
}
