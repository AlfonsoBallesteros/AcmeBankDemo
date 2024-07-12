package com.acme.bank.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @Column(length = 50)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        return name != null && name.equals(((Authority) o).name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
