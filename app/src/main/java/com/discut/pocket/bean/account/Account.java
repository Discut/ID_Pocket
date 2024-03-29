package com.discut.pocket.bean.account;


import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.Tag;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String account;
    private String password;
    private String note;
    private Tag[] tags;
    private AccountStatus status;

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = Long.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
