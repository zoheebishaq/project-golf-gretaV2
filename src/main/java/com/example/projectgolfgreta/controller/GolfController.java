package com.example.projectgolfgreta.controller;

import com.example.projectgolfgreta.formdata.GolfFormDTO;
import com.example.projectgolfgreta.formdata.ParcoursFormDTO;
import com.example.projectgolfgreta.formdata.TrouFormDTO;
import com.example.projectgolfgreta.models.Golf;
import com.example.projectgolfgreta.models.Parcours;
import com.example.projectgolfgreta.models.Trou;
import com.example.projectgolfgreta.service.GolfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GolfController {
    private GolfService golfService;
    @Autowired
    public GolfController(GolfService golfService) {
        this.golfService = golfService;
    }

    //Index-------------------------------------------------------------------------------------------------------------
    @GetMapping("/")
    public String index() {
        return "hello";
    }

    //Golf--------------------------------------------------------------------------------------------------------------
    @GetMapping("/golf")
    public String golf(Model model) {
        model.addAttribute("golf", golfService.getGolfs());

        return "golfList";
    }

    @GetMapping("/golf/{id}")
    public String golfs(Model model, @PathVariable(name = "id") Long id) {
        Golf golf = golfService.getGolf(id);
        GolfFormDTO golfFormDTO = new GolfFormDTO();
        golfFormDTO.setId(golf.getId());
        golfFormDTO.setNom(golf.getNom());
        golfFormDTO.setCoordGPS(golf.getCoordGPS());
        model.addAttribute("golfs", golf);

        return "golfForm";
    }

    @GetMapping("/golf/form")
    public String addGolfs(Model model) {
        model.addAttribute("golfs", new GolfFormDTO());
        return "golfForm";
    }

    @GetMapping("/golf/delete{id}")
    public String deleteGolfs(@PathVariable(name = "id") Long id) {
        golfService.deleteGolfs(id);
        return "redirect:/golf";
    }

    @PostMapping("/golf")
    public String postGolfs(@ModelAttribute(name = "golf") GolfFormDTO golfFormDTO) {
        golfService.saveGolf(golfFormDTO);
        return "redirect:/golf";
    }

    //Parcours----------------------------------------------------------------------------------------------------------
    @GetMapping("/golf/parcours/{idGolf}")
    public String parcour(Model model, @PathVariable Long idGolf) {
        ParcoursFormDTO parcoursFormDTO = new ParcoursFormDTO();
        parcoursFormDTO.setGolfId(idGolf);
        List<Trou> trou = new ArrayList<Trou>();
        for (int i = 0; i < 18; i++) {
            trou.add(new Trou(i+1));
        }
        parcoursFormDTO.setTrous(trou);
        model.addAttribute("parcours",parcoursFormDTO);
        return "parcoursAddForm";
    }

    @GetMapping("/golf/updateParcours/{id}")
    public String parcours(Model model, @PathVariable(name = "id") Long id) {
        Parcours parcours = golfService.getParcour(id);
        ParcoursFormDTO parcoursFormDTO = new ParcoursFormDTO();
        parcoursFormDTO.setId(parcours.getId());
        parcoursFormDTO.setNom(parcours.getNom());
        parcoursFormDTO.setGolfId(parcours.getGolf().getId());
        parcoursFormDTO.setTrous(parcours.getTrous());

        List<Golf> golf = golfService.getGolfs();

        model.addAttribute("parcours", parcoursFormDTO);
        model.addAttribute("golf", golf);

        return "parcoursUpdateForm";
    }

    @GetMapping("golf/parcours/form")
    public String addParcours(Model model) {
        model.addAttribute("parcours", new ParcoursFormDTO());
        List<Golf> golfList = golfService.getGolfs();
        model.addAttribute("golf", golfList);
        return "parcoursUpdateForm";
    }

    @GetMapping("/golf/parcours/delete{id}")
    public String deleteParcours(@PathVariable(name = "id") Long id) {
        System.out.println(id);
        golfService.deleteParcours(id);
        return "redirect:/golf";
    }

    @PostMapping("/golf/parcours")
    public String postParcours(@ModelAttribute(name = "parcours") ParcoursFormDTO parcoursFormDTO) {
        golfService.saveParcours(parcoursFormDTO);
        return "redirect:/golf";
    }

    //Trou--------------------------------------------------------------------------------------------------------------

    @GetMapping("/trou")
    public String trou(Model model) {
        model.addAttribute("trou", golfService.getTrous());
        model.addAttribute("parcours", golfService.geParcours());
        model.addAttribute("golf",golfService.getGolfs());
        return "trouList";
    }


    @PostMapping("/trou")
    public String postTrou(@ModelAttribute(name = "trou") TrouFormDTO trouFormDTO) {
        golfService.saveTrou(trouFormDTO);
        return "redirect:/golf/parcours";
    }


}

