package de.heimbrauconvention.votingservice;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.repository.CompetitionRepository;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private CompetitionRepository competitionRepository;
    private RatingCodeRepository ratingCodeRepository;
    
 
    @Autowired
    public DataLoader(
    		CompetitionRepository competitionRepository,
    		RatingCodeRepository ratingCodeRepository
        ) {
        this.competitionRepository = competitionRepository;
        this.ratingCodeRepository = ratingCodeRepository;
    }

    @Transactional
    public void run(ApplicationArguments args) {
    	
    	Iterable<Competition> competitions = competitionRepository.findAll();
    	
    	if (competitions == null || !competitions.iterator().hasNext()) {
    		
    		Competition competition1 = new Competition();
        	competition1.setTitle("HB-CON Publikumspreis");
        	competition1.setDescription(""
        			+ "<h1>Alljährlicher Publikumspreis der Hobbybrauconvention 2020</h1>"
        			+ "<p>Auch in diesem Jahr präsentieren ambitionierte Hobbybrauer ihre Biere der Öffentlichkeit und stellen sich dem Wettbewerb um die begehrten Trophehen, Ruhm und Ehre.</p>");
        	competition1.setEmail("schloemi@schloemi.de");
        	
        	
        	        	
        	/**
        	 * 
        	 * update competition set start_date = '2020-03-14 17:00:00' where id = 1;
        	 * update competition set end_date = '2020-03-14 19:00:00' where id = 1;
        	 */
            competition1.setStartDate(new GregorianCalendar(2020, Calendar.MARCH, 14, 17, 00, 00).getTime());
        	competition1.setEndDate(new GregorianCalendar(2020, Calendar.MARCH, 14, 19, 00, 00).getTime());
        	competition1.setLocation("Schloss Romrod");
        	
        	
        	competition1.setIsActive(Boolean.TRUE);
        	
        	Competition competition2 = new Competition();
        	competition2.setTitle("Poster Session");
        	competition2.setDescription(""
        			+ "<h1>Neu auf der HB-COM 2020</h1>"
        			+ "<p>Erstmalig startet in diesem Jahr der erbitterte Kampf um die goldene Krone des Poster-Master </p>");
        	competition2.setEmail("schloemi@schloemi.de");
        	competition2.setMaxRatingPerCode(2);
        	competition2.setStartDate(Calendar.getInstance().getTime());
        	competition2.setIsActive(Boolean.TRUE);
        	competition2.setStartDate(new GregorianCalendar(2020, Calendar.MARCH, 13, 17, 30, 00).getTime());
         	competition2.setEndDate(new GregorianCalendar(2020, Calendar.MARCH, 13, 19, 30, 00).getTime());
         	competition2.setLocation("Schloss Romrod");
        	
        	
        	
        	Competition competition3 = new Competition();
        	competition3.setTitle("Hopfen Queen");
        	competition3.setDescription(""
        			+ "<h1>Es lebe die Hopfen-Queen</h1>"
        			+ "<p>Es lebe hoch die Hopfen-Queen, hoch lebe die Hopfen-Queen</p>");
        	competition3.setEmail("schloemi@schloemi.de");
        	competition3.setMaxRatingPerCode(1);
        	competition3.setIsActive(Boolean.TRUE);
        	competition3.setStartDate(new GregorianCalendar(2020, Calendar.MARCH, 13, 20, 15, 00).getTime());
         	competition3.setEndDate(new GregorianCalendar(2020, Calendar.MARCH, 13, 22, 15, 00).getTime());
         	competition3.setLocation("Bürgerhaus Romrod");
        	
        	
        	/**
        	 * 
        	 * this save is just for pretty easy sequence number
        	 * 
        	 */
        	competitionRepository.save(competition1);
        	competitionRepository.save(competition2);
        	competitionRepository.save(competition3);
        	
        	for (int i = 1; i < 101; i++) {
        		RatingItem item = new RatingItem(competition1);
        		item.setTitle("Bier mit der Nummer: " + i);
        		item.setDescription("Das schärfste Bier im Wettbewerb ist die Nummer " + i);
        		item.setIsActive(Boolean.TRUE);
        		competition1.getRatingItems().add(item);
        	}
        	
        	for (int i = 1; i < 11; i++) {
        		RatingItem item = new RatingItem(competition2);
        		item.setTitle("Poster mit der Nummer: " + i);
        		item.setDescription("Das tollste Poster im Stall hat Nummer " + i);
        		item.setIsActive(Boolean.TRUE);
        		competition2.getRatingItems().add(item);
        	}
        	
        	for (int i = 1; i < 23; i++) {
        		RatingItem item = new RatingItem(competition3);
        		item.setTitle("Hopfenqueen Nummer: " + i);
        		item.setDescription("Hopfen-Queen Nummer " + i);
        		item.setIsActive(Boolean.TRUE);
        		competition3.getRatingItems().add(item);
        	}
        	
        	competitionRepository.save(competition1);
        	competitionRepository.save(competition2);
        	competitionRepository.save(competition3);
        	
        	Set<Competition> comps = new HashSet<Competition>();
        	comps.add(competition1);
        	comps.add(competition2);
        	comps.add(competition3);
        	
        	for (int i = 0; i < 1000; i++) {
        		RatingCode code = new RatingCode(comps);
        		code.setIsActive(i%3 == 0);
        		ratingCodeRepository.save(code);
        	}
      	}
    }
    
}