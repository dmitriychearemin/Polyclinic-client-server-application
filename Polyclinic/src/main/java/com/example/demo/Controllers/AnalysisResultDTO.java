package com.example.demo.Controllers;

import com.example.demo.Entities.AnalysisResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResultDTO {
    private Long id;
    private String testType;
    private LocalDate testDate;
    private String result;
    private String doctorName;
    private String doctorId;


    public AnalysisResultDTO(AnalysisResult analysis) {
        this.id = analysis.getId();
        this.testType = analysis.getTestType();
        this.testDate = analysis.getTestDate();
        this.result = analysis.getResult();
        this.doctorName = analysis.getDoctor() != null ?
                analysis.getDoctor().getFirstName() + " " + analysis.getDoctor().getLastName() : "N/A";
        this.doctorId = analysis.getDoctor().getId().toString();

    }

}