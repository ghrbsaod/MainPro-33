package main.main.salarystatement.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.memberbank.entity.MemberBank;
import main.main.statusofwork.entity.StatusOfWork;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class SalaryStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "MEMBER_BANK_ID")
    private MemberBank memberBank;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private int year;

    private int month;

    private double hourlyWage;

    private double basePay; // 기본급

    private double overtimePay; // 연장근로수당

    private int overtimePayBasis;

    private double nightWorkAllowance; // 야간근로수당

    private int nightWorkAllowanceBasis;

    private double holidayWorkAllowance; // 휴일근로수당

    private int holidayWorkAllowanceBasis;

    private double salary;

    public void setSalary() {
        this.salary += basePay + overtimePay + nightWorkAllowance + holidayWorkAllowance;
    }

    private boolean paymentStatus;

    @OneToMany(mappedBy = "salaryStatement")
    private List<StatusOfWork> statusOfWorks = new ArrayList<>();

    public void addCalculationOfSalary(StatusOfWork statusOfWork) {
        this.statusOfWorks.add(statusOfWork);
    }
}
