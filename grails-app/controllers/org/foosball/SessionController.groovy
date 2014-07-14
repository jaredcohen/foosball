package org.foosball

import com.memetix.random.RandomNumberGenerator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.foosball.Playoff;
import org.foosball.Session;
import org.foosball.Team;
import org.foosball.SecUser;
import org.springframework.dao.DataIntegrityViolationException;
import org.uncommons.maths.random.MersenneTwisterRNG;

class SessionController {
	
	def index() {
		redirect(action: "list");
	}

	def list() {
		[personInstanceList: Person.list()]
	}

	def pickTeams() {
		def sessionId = Session.getCurrentSession();
		if (params.markUnplayedGamesAsLosses) {
			markUnplayedGamesAsLosses(sessionId);
		}

		List<Person> players = new ArrayList<Person>();
		for (int i = 1; i <= Person.count(); i++) {
			if (params.get(i.toString())) {
				def player = Person.get(i);
				players.add(player);
			}
		}
		
		if (players.size() % 2 != 0) {
			flash.message = "Please select an even number of players.";
			redirect(action: "list", 'params':params);
			return;
		}
		
		players.sort();
		Integer median = (players.size() / 2);
		List<Person> upperTier = players.subList(0, median);
		List<Person> lowerTier = players.subList(median, (players.size()));
		
		int newSessionId = sessionId + 1;
		Session newSession = new Session('description': createNewSessionDescription());
		newSession.id = newSessionId;
		if (newSession.save()) {
			//Any unplayed games are set as losses
			
			//Create New Teams
			List<Team> newTeams = createNewTeams(upperTier, lowerTier, sessionId);
			newTeams.each { team ->
				if (!team.save()) {
					flash.message = "An error occurred saving team: " + team.getName() + ". The other teams should not be affected however.";
				}
			}
		} else {
			flash.message = "An error occurred saving the new session to the db.";
		}
		redirect(controller: "table", action: "index")
	}
	
	def markUnplayedGamesAsLosses(Integer id) {
		if (id == null || Session.get(id) == null) {
			flash.message = "An invalid session number was provided";
			redirect(controller: "table", action: "index");
		}
		def teamDidNotPlay = Team.find { name == 'DNP' };
		def teamInstanceList = Team.findAll { sessionId == id };
		teamInstanceList.each { team ->
			def unplayedTeamsCount = team.getTeamsNotPlayed().size();
			if (unplayedTeamsCount > 0 ) {
				for (int i = 0; i < unplayedTeamsCount; i++) {
					def dnpResult = new Result(['winner': teamDidNotPlay, 'opponent': team, 'sessionId': id]);
					if (!dnpResult.save()) {
						flash.message = "An error occurred marking uplayed games from last session as losses for team: " + team.getName() + ". The other teams should not be affected however.";
					}
				}
			}
		}
	}
	
	private List<Team> createNewTeams(List<Person> upperTier, List<Person> lowerTier, Integer sessionId) {
		
		boolean teamsValid = true;
		List<Team> newTeams = new ArrayList<Team>();		
		Random rng = RandomNumberGenerator.MERSENNE_TWISTER.rng;
		Collections.shuffle(upperTier, rng);
		Collections.shuffle(lowerTier, rng);
		for (int i = 0; i < upperTier.size(); i++) {
			Person upperTierPartner = upperTier.get(i);
			Person lowerTierPartner = lowerTier.get(i);
			
			Team lastTeam = getLastTeamForPerson(upperTierPartner, sessionId);
			if (lastTeam != null) {
				if (lastTeam.getMembers().contains(lowerTierPartner)) {
					teamsValid = false;
				}
			}
			newTeams.add(new Team(['name': upperTierPartner.getName() + ' & ' + lowerTierPartner.getName(), 'members': [upperTierPartner, lowerTierPartner], 'sessionId': sessionId + 1]));
		}
		if (!teamsValid) {
			return createNewTeams(upperTier, lowerTier, sessionId);
		}
		return newTeams;
		
	}
	
	private Team getLastTeamForPerson(Person person, Integer sessionId) {
		Set<Team> teams = person.getTeams();
		Team lastTeam = null;
		teams.each { team ->
			if (team.getSessionId() == sessionId) {
				lastTeam = team;
			}
		}
		return lastTeam;
	}
	
	private String createNewSessionDescription() {
		DateFormat sdf = new SimpleDateFormat("MM/dd");
		StringBuilder sb = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		sb.append(sdf.format(calendar.getTime()));
		calendar.add(Calendar.DATE, 14);
		sb.append(" - " + sdf.format(calendar.getTime()));
		return sb.toString();
	}
}