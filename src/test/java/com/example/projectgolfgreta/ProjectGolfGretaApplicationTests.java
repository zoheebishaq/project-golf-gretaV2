package com.example.projectgolfgreta;

import com.example.projectgolfgreta.formdata.GolfFormDTO;
import com.example.projectgolfgreta.models.Golf;
import com.example.projectgolfgreta.service.GolfService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectGolfGretaApplicationTests {

	@Autowired
	GolfService golfService;
	@Test
	void contextLoads() {

	}

	@Transactional
	@Test
	public void createGolf(){
		int nbrGolfT0 = golfService.getGolfs().size();
		GolfFormDTO golfFormDTO = new GolfFormDTO();
		golfFormDTO.setNom("Boissy");
		golfService.saveGolf(golfFormDTO);
		int nbrGolfT1 = golfService.getGolfs().size();
		assertThat(nbrGolfT1).isEqualTo(nbrGolfT0+1);
		Golf golf = golfService.getGolfs().get(nbrGolfT1-1);
		assertThat(golf.getNom()).isEqualTo("Boissy");
	}

}
