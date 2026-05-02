package com.guptaji.springboot_learning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orgData")
public class Organization {

    @Id
    private Long orgId;
    private String orgName;
    private int branches;
    private Long noOfEmployees;


    public int getBranches() {
        return branches;
    }

    public void setBranches(int branches) {
        this.branches = branches;
    }

    public Long getNoOfEmployees() {
        return noOfEmployees;
    }

    public void setNoOfEmployees(Long noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}
