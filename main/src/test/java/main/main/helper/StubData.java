package main.main.helper;

import main.main.auth.jwt.JwtTokenizer;
import main.main.bank.dto.BankDto;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.entity.CompanyMember;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.memberbank.dto.MemberBankDto;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class StubData {
    public static class MockSecurity {
        public static String getValidAccessToken(String secretKey) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            claims.put("memberId", 1L);

            String subject = "test access token";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            Date expiration = calendar.getTime();

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

            return accessToken;
        }
    }

    public static class MockLaborContract {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            LocalDateTime time = LocalDateTime.now();

            LaborContractDto.Post post = new LaborContractDto.Post();
            post.setMemberId(1L);
            post.setCompanyId(1L);
            post.setBasicSalary(BigDecimal.valueOf(3000000));
            post.setStartOfContract(time);
            post.setEndOfContract(time);
            post.setStartTime(LocalTime.MIDNIGHT);
            post.setFinishTime(LocalTime.MIDNIGHT);
            post.setInformation("근로계약서 정보");

            LaborContractDto.Patch patch = new LaborContractDto.Patch();
            patch.setBasicSalary(BigDecimal.valueOf(3200000));
            patch.setStartTime(LocalTime.MIDNIGHT);
            patch.setFinishTime(LocalTime.MIDNIGHT);
            patch.setInformation("수정된 근로계약서 정보");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static LaborContractDto.Response getResponseBody() {
            return LaborContractDto.Response.builder()
                    .memberName("직원 이름")
                    .companyName("회사 이름")
                    .basicSalary(BigDecimal.valueOf(3000000))
                    .startTime(LocalTime.MIDNIGHT)
                    .finishTime(LocalTime.MIDNIGHT)
                    .information("근로계약서 정보").build();
        }

        public static HashMap<byte[], String> getImage() throws IOException {
            InputStream inputStream = new FileInputStream("mock/image.png");
            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            HashMap<byte[], String> imageMap = new HashMap<>();
            imageMap.put(imageByteArray, "png");
            return imageMap;
        }
    }

    public static class MockStatusOfWork {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            LocalDateTime time = LocalDateTime.now();

            StatusOfWorkDto.Post post = new StatusOfWorkDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setStartTime(time);
            post.setFinishTime(time);
            post.setNote(StatusOfWork.note.지각);

            StatusOfWorkDto.Patch patch = new StatusOfWorkDto.Patch();
            patch.setStartTime(time);
            patch.setFinishTime(time);
            patch.setNote(StatusOfWork.note.결근);

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static List<StatusOfWork> getStatusOfWorkList() {
            return List.of(
                    new StatusOfWork(),
                    new StatusOfWork()
            );
        }

        public static StatusOfWorkDto.Response getResponseBody() {
            LocalDateTime time = LocalDateTime.now();

            return StatusOfWorkDto.Response.builder()
                    .id(1L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build();
        }

        public static List<StatusOfWorkDto.Response> getMultiResponseBody() {
            LocalDateTime time = LocalDateTime.now();

            return List.of(
                    StatusOfWorkDto.Response.builder()
                    .id(1L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build(),
                    StatusOfWorkDto.Response.builder()
                    .id(2L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build());
        }
    }
    public static class MockSalaryStatement {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            SalaryStatementDto.Post post = new SalaryStatementDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setYear(2023);
            post.setMonth(1);

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static SalaryStatementDto.Response getResponseBody() {
            return SalaryStatementDto.Response.builder()
                    .id(1L)
                    .companyId(1L)
                    .companyName("회사 이름")
                    .memberId(1L)
                    .memberName("직원 이름")
                    .year(2023)
                    .month(1)
                    .hourlyWage(BigDecimal.valueOf(10000))
                    .basePay(BigDecimal.valueOf(2090000))
                    .overtimePay(BigDecimal.valueOf(0))
                    .overtimePayBasis(0)
                    .nightWorkAllowance(BigDecimal.valueOf(0))
                    .nightWorkAllowanceBasis(0)
                    .holidayWorkAllowance(BigDecimal.valueOf(0))
                    .holidayWorkAllowanceBasis(0)
                    .unpaidLeave(BigDecimal.valueOf(0))
                    .salary(BigDecimal.valueOf(2090000))
                    .incomeTax(BigDecimal.valueOf(10000))
                    .nationalCoalition(BigDecimal.valueOf(10000))
                    .healthInsurance(BigDecimal.valueOf(10000))
                    .employmentInsurance(BigDecimal.valueOf(10000))
                    .totalSalary(BigDecimal.valueOf(2050000))
                    .bankName("국민")
                    .accountNumber("111-111111-11-11")
                    .build();
        }
    }


    public static class MockCompany {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {

            CompanyDto.Post post = new CompanyDto.Post();
            post.setCompanyName("회사명");
            post.setCompanySize("회사 규모");
            post.setAddress("회사 주소");
            post.setInformation("회사 정보");

            CompanyDto.Patch patch = new CompanyDto.Patch();
            patch.setCompanyId(1L);
            patch.setCompanyName("회사명");
            patch.setCompanySize("회사 규모");
            patch.setBusinessNumber("사업자 등록 번호");
            patch.setAddress("회사 주소");
            patch.setInformation("회사 정보");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }

        public static Page<Company> getCompaniesByPage() {
            Company company1 = new Company();
            Company company2 = new Company();

            return new PageImpl<>(List.of(company1, company2), PageRequest.of(0, 5, Sort.by("companyId").descending()),2);

        }

        public static CompanyDto.Response getCompanyResponse() {

            return CompanyDto.Response.builder()
                    .companyId(1L)
                    .companyName("회사명")
                    .companySize("회사 규모")
                    .businessNumber("사업자 등록 번호")
                    .address("회사 주소")
                    .information("회사 정보")
                    .build();
        }

//        public static CompanyDto.ResponseForSalary getCompanyResponseForSalary() {
//
//            return CompanyDto.ResponseForSalary.builder()
//                    .companyId(1L)
//                    .companyName("회사명")
//                    .totalSalaryOfCompany(BigDecimal.valueOf(10000))
//                    .build();
//        }

        public static List<CompanyDto.ResponseForList> getCompaniesToCompaniesResponse() {
            return List.of(
                    CompanyDto.ResponseForList.builder()
                            .companyId(1L)
                            .companyName("회사명")
                            .companySize("회사 규모")
                            .businessNumber("사업자 등록 번호")
                            .address("회사 주소")
                            .information("회사 정보")
                            .build(),
                    CompanyDto.ResponseForList.builder()
                            .companyId(2L)
                            .companyName("회사명")
                            .companySize("회사 규모")
                            .businessNumber("사업자 등록 번호")
                            .address("회사 주소")
                            .information("회사 정보")
                            .build()
            );
        }
    }


    public static class MockMemberBank {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {

            MemberBankDto.Post post = new MemberBankDto.Post();
            post.setMemberId(1L);
            post.setBankId(1L);
            post.setAccountNumber("회원 계좌 번호");

            MemberBankDto.Patch patch = new MemberBankDto.Patch();
            patch.setMemberBankId(1L);
            patch.setMemberId(1L);
            patch.setBankId(1L);
            patch.setAccountNumber("회원 계좌 번호");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }


        public static MemberBankDto.Response getMemberBankResponse() {
            return MemberBankDto.Response.builder()
                    .memberBankId(1L)
                    .memberId(1L)
                    .bankId(1L)
                    .bankName("회원 계좌 은행명")
                    .accountNumber("회원 계좌 번호")
                    .build();
        }
    }


    public static class MockBank {

        public static BankDto.Response getBankResponse() {
            return BankDto.Response.builder()
                    .bankId(1L)
//                    .bankCode("회원 계좌 은행 코드")
                    .bankName("회원 계좌 은행명")
                    .build();
        }
    }


    public static class MockCompanyMember {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            CompanyMemberDto.Post post = new CompanyMemberDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setGrade("회원 직급");
            post.setTeam("회원 소속 부서");

            CompanyMemberDto.Patch patch = new CompanyMemberDto.Patch();
            patch.setCompanyMemberId(1L);
            patch.setCompanyId(1L);
            patch.setMemberId(1L);
            patch.setGrade("회원 직급");
            patch.setTeam("회원 소속 부서");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }

        public static Page<CompanyMember> getCompanyMembersByPage() {
            CompanyMember companyMember1 = new CompanyMember();
            CompanyMember companyMember2 = new CompanyMember();

            return new PageImpl<>(List.of(companyMember1, companyMember2), PageRequest.of(0, 5, Sort.by("companyMemberId").descending()),2);

        }

        public static CompanyMemberDto.Response getCompanyMemberResponse() {
            return CompanyMemberDto.Response.builder()
                    .companyMemberId(1L)
                    .companyId(1L)
                    .memberId(1L)
                    .grade("회원 직급")
                    .team("회원 소속 부서")
                    .build();
        }

        public static List<CompanyMemberDto.ResponseForList> getCompanyMembersToCompanyMembersResponse() {
            return List.of(
                    CompanyMemberDto.ResponseForList.builder()
                            .companyMemberId(1L)
                            .companyId(1L)
                            .memberId(1L)
                            .grade("회원 직급")
                            .team("회원 소속 부서")
                            .build(),
                    CompanyMemberDto.ResponseForList.builder()
                            .companyMemberId(2L)
                            .companyId(1L)
                            .memberId(1L)
                            .grade("회원 직급")
                            .team("회원 소속 부서")
                            .build()
            );
        }
    }
}
