/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {
    public  Account() {}
    public  Account(String accountType, BigDecimal balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    @Id
    //@SequenceGenerator(name="account_id_generator", sequenceName="account_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy=SEQUENCE, generator="account_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name ="account_type")
    private String accountType;

    @Column(name = "balance")
    private BigDecimal balance;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    //fetch_type=lazy select * from accounts
    //HQL fetch_type=eager select a from Account a join fetch a.employee where a.employee.id=:Id
    //native sql fetch_type=eager select * from accounts as a left join employees as e on a.employee_id=e.id where e.id=:Id

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                account.balance.subtract(balance).equals(BigDecimal.ZERO) &&
                accountType.equals(account.accountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, balance);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        }
        catch(JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        return str;
    }
}
