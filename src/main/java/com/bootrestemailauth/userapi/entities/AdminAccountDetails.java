package com.bootrestemailauth.userapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "account_details_admin")
public class AdminAccountDetails {

    @Id
    @Column(name = "admin_id")
    private int adminId;   
    
    @Column(name = "account_number")
    private String accountNo;

    @Column(name = "ifsc_code")
    private String ifsc_code;

    @Column(name = "bank_name")
    private String bank_name;

    @Column(name = "branch_name")
    private String branch_name;

    @Column(name = "account_holder_name")
    private String acc_holder_name;

    public AdminAccountDetails() {
    }

    public AdminAccountDetails(int adminId, String accountNo, String ifsc_code, String bank_name, String branch_name,
            String acc_holder_name) {
        this.adminId = adminId;
        this.accountNo = accountNo;
        this.ifsc_code = ifsc_code;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
        this.acc_holder_name = acc_holder_name;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(String acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    @Override
    public String toString() {
        return "AdminAccountDetails [acc_holder_name=" + acc_holder_name + ", accountNo=" + accountNo + ", adminId="
                + adminId + ", bank_name=" + bank_name + ", branch_name=" + branch_name + ", ifsc_code=" + ifsc_code
                + "]";
    }

    
}
