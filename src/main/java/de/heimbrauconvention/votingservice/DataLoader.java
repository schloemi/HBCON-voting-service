package de.heimbrauconvention.votingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.repository.CompetitionRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private CompetitionRepository competitionRepository;
 
    @Autowired
    public DataLoader(
    		CompetitionRepository competitionRepository
        ) {
        this.competitionRepository = competitionRepository;
    }

    
    public void run(ApplicationArguments args) {
    	
    	
    	Iterable<Competition> competitions = competitionRepository.findAll();
    	
    	if (competitions == null || !competitions.iterator().hasNext()) {
    		Competition competition = new Competition();
        	competition.setTitle("HBCON Publikumspreis");
        	competition.setDescription("<h1>Alljährlicher Publikumspreis der Hobbybrauconvention 2020</h1>");
        	competition.setEmail("schloemi@schloemi.de");
        	competition.setIsActive(Boolean.TRUE);
        	
        	for (int i = 0; i < 23; i++) {
        		RatingItem item = new RatingItem(competition);
        		item.setTitle("Bier mit der Nummer: " + i);
        		item.setDescription("Das schärfste Bier im Wettbewerb ist die Nummer " + i);
        		item.setIsActive(Boolean.TRUE);
        		competition.getRatingItems().add(item);
        	}
        
        	for (int i = 0; i < 100; i++) {
        		RatingCode code = new RatingCode(competition);
        		competition.getRatingCodes().add(code);
        	}
        	
        	competitionRepository.save(competition);
      	}
    	
    }
    
}