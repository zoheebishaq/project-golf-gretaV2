package com.example.projectgolfgreta.formdata;

import com.example.projectgolfgreta.models.Ajustement;
import com.example.projectgolfgreta.models.Trou;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TourFormDTO {
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private LocalTime heurePremierDepart;
    private int intervalleDepart;
    private int nbJoueursParPartie;
    private int numTour;
    private long tournoiId;
    private MultipartFile file;

    private List<Ajustement> ajustements;

    //TODO CSV


}
