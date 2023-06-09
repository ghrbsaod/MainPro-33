package main.main.laborcontract.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LaborContractDto {
    @Getter
    @Setter
    public static class Post {
        private long memberId;
        private long companyId;
        private BigDecimal basicSalary;
        private LocalDateTime startOfContract;
        private LocalDateTime endOfContract;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Setter
    public static class Patch {
        private BigDecimal basicSalary;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Builder
    public static class Response {
        private String memberName;
        private String companyName;
        private String bankName;
        private String accountNumber;
        private String accountHolder;
        private BigDecimal basicSalary;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }
}
