package main.main.companymember.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import main.main.company.entity.Company;
import main.main.companymember.dto.Authority;
import main.main.companymember.dto.Status;
import main.main.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long companyMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnoreProperties("companyMembers")
    private Company company;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnoreProperties("companyMembers")
    private Member member;

    private String grade;
    private String team;

    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setCompany(Company company) {
        this.company = company;
        if(!company.getCompanyMembers().contains(this)) {
            company.getCompanyMembers().add(this);
        }
    }
}
