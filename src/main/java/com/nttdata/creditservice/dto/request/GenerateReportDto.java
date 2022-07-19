package com.nttdata.creditservice.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenerateReportDto {

    private String creditId;
    private LocalDate startDate;
    private LocalDate endDate;
}
