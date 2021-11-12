package com.example.exampleproject;

public class CustomAccountModel {

    int accountNo;
    String accountType;
    int branchNo;
    String branchName;
    int accountBalance;

    public CustomAccountModel(int accountNo, String accountType, int branchNo, String branchName, int accountBalance) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.branchNo = branchNo;
        this.branchName = branchName;
        this.accountBalance = accountBalance;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(int branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}
