package org.foosball

class Team implements Comparable {

	int id;
	String name;
	Integer session;

	static transients = [
		'winCount',
		'lossCount',
		'winPct',
		'gamesWon',
		'gamesLost',
		'gamesWinPct',
		'goalsFor',
		'goalsAgainst',
		'goalDiff',
		'playoffWinCount',
		'playoffLossCount',
		'playoffGamesWon',
		'playoffGamesLost',
		'playoffGoalsFor',
		'playoffGoalsAgainst',
		'teamsPlayed',
		'teamsNotPlayed',
		'finalTeamPosition',
		'finalTeamPositionRating'
	]

	static constraints = {
	}

	static hasMany = [ members: Person, wins: Result, losses: Result, playoffWins: Playoff, playoffLosses: Playoff]

	static mappedBy = [wins: 'winner', losses: 'opponent', playoffWins: 'winner', playoffLosses: 'opponent']

	static mapping = { members joinTable: [name: 'TEAM_MEMBER', column: 'ID', key: 'TEAM_ID'] }

	Integer getWinCount() {
		if (wins != null) {
			return wins.size()
		}
		return 0
	}

	Integer getLossCount() {
		if (losses != null) {
			return losses.size();
		}
		return 0;
	}

	Double getWinPct() {
		Double winPct = new Double("0.0");
		if (getWinCount() > 0) {
			winPct = getWinCount() / (getWinCount() + getLossCount());
		}
		return (winPct * 100).round(2);
	}

	Integer getGamesWon() {
		Integer gamesWon = 0;
		wins.each { result ->
			gamesWon += result.getGamesWonByWinner();
		}
		losses.each { result ->
			gamesWon += result.getGamesWonByLoser();
		}
		return gamesWon;
	}

	Integer getGamesLost() {
		Integer gamesLost = 0;
		wins.each { result ->
			gamesLost += result.getGamesWonByLoser();
		}
		losses.each { result ->
			gamesLost += result.getGamesWonByWinner();
		}
		return gamesLost;
	}

	Double getGamesWinPct() {
		Double gamesWinPct = new Double("0.0");
		if (getGamesWon() > 0) {
			gamesWinPct = getGamesWon() / (getGamesWon() + getGamesLost());
		}
		return (gamesWinPct * 100).round(2);
	}

	Integer getGoalsFor() {
		Integer goalsFor = 0;
		wins.each { result ->
			goalsFor += result.getGoalsForWinner();
		}
		losses.each { result ->
			goalsFor += result.getGoalsForLoser();
		}
		return goalsFor;
	}

	Integer getGoalsAgainst() {
		Integer goalsAgainst = 0;
		wins.each { result ->
			goalsAgainst += result.getGoalsForLoser();
		}
		losses.each { result ->
			goalsAgainst += result.getGoalsForWinner();
		}
		return goalsAgainst;
	}

	Integer getGoalDiff() {
		return getGoalsFor() - getGoalsAgainst();
	}

	Integer getPlayoffWinCount() {
		if (playoffWins != null) {
			return playoffWins.size()
		}
		return 0
	}

	Integer getPlayoffLossCount() {
		if (playoffLosses != null) {
			return playoffLosses.size();
		}
		return 0;
	}

	Integer getPlayoffGamesWon() {
		Integer gamesWon = 0;
		playoffWins.each { result ->
			gamesWon += result.getGamesWonByWinner();
		}
		playoffLosses.each { result ->
			gamesWon += result.getGamesWonByLoser();
		}
		return gamesWon;
	}

	Integer getPlayoffGamesLost() {
		Integer gamesLost = 0;
		playoffWins.each { result ->
			gamesLost += result.getGamesWonByLoser();
		}
		playoffLosses.each { result ->
			gamesLost += result.getGamesWonByWinner();
		}
		return gamesLost;
	}

	Integer getPlayoffGoalsFor() {
		Integer playoffGoalsFor = 0;
		playoffWins.each { result ->
			playoffGoalsFor += result.getGoalsForWinner();
		}
		playoffLosses.each { result ->
			playoffGoalsFor += result.getGoalsForLoser();
		}
		return playoffGoalsFor;
	}

	Integer getPlayoffGoalsAgainst() {
		Integer playoffGoalsAgainst = 0;
		playoffWins.each { result ->
			playoffGoalsAgainst += result.getGoalsForLoser();
		}
		playoffLosses.each { result ->
			playoffGoalsAgainst += result.getGoalsForWinner();
		}
		return playoffGoalsAgainst;
	}

	Integer getPlayoffGoalDiff() {
		return getPlayoffGoalsFor() - getPlayoffGoalsAgainst();
	}
	
	List<Team> getTeamsPlayed() {
		List<Team> teamsPlayed = new ArrayList<Team>();
		wins.each { result ->
			teamsPlayed.add(result.getOpponent());
		}
		losses.each { result ->
			teamsPlayed.add(result.getWinner());
		}
		return teamsPlayed;
	}
	
	List<Team> getTeamsNotPlayed() {
		def c = Team.createCriteria();
		List<Team> allTeams = c.list {
			eq("session", session)
			and {
				ne("id", id)
				
			}
		}
		List<Team> teamsNotPlayed = new ArrayList<Team>();
		allTeams.each { team -> 
			if (!getTeamsPlayed().contains(team)) {
				teamsNotPlayed.add(team);
			}
		}
		return teamsNotPlayed;
	}
	
	
	Integer getFinalTeamPosition() {
		Session sessionObject = Session.get(session);
		Integer finalPlayoffRoundNum = sessionObject.getFinalPlayoffRoundNum();
		Playoff finalMatch = Playoff.createCriteria().get {
			eq("sessionId", id)
			and {
				eq("roundNum", finalPlayoffRoundNum)
				eq("finalMatch", true)
			}
		}
		if (finalMatch != null) {
			if (finalMatch.getWinner().equals(this)) {
				return 1;
			}
			if (finalMatch.getOpponent().equals(this)) {
				return 2;
			}
		}
		
		Playoff consolationMatch = Playoff.createCriteria().get {
			eq("sessionId", id)
			and {
				eq("roundNum", finalPlayoffRoundNum)
				eq("finalMatch", true)
			}
		}
		if (consolationMatch != null) {
			if (consolationMatch.getWinner().equals(this)) {
				return 3;
			}
			if (consolationMatch.getOpponent().equals(this)) {
				return 4;
			}
		}
	}
	
	Double getFinalTeamPositionRating() {
		Session sessionObject = Session.get(session);
		Integer finalPlayoffRoundNum = sessionObject.getFinalPlayoffRoundNum();
		Playoff finalMatch = Playoff.createCriteria().get {
			eq("sessionId", id)
			and {
				eq("roundNum", finalPlayoffRoundNum)
				eq("finalMatch", true)
			}
		}
		if (finalMatch != null) {
			if (finalMatch.getWinner().equals(this)) {
				return 1;
			}
			if (finalMatch.getOpponent().equals(this)) {
				return 0.5;
			}
		}
		
		Playoff consolationMatch = Playoff.createCriteria().get {
			eq("sessionId", id)
			and {
				eq("roundNum", finalPlayoffRoundNum)
				eq("finalMatch", true)
			}
		}
		if (consolationMatch != null) {
			if (consolationMatch.getWinner().equals(this)) {
				return 0.25;
			}
			if (consolationMatch.getOpponent().equals(this)) {
				return 0.1;
			}
		}
	}

	@Override
	public int compareTo(Object o) {
		def other = (Team) o;
		if (other.getWinPct() != getWinPct()) {
			return other.getWinPct().compareTo(getWinPct());
		}
		if (other.getGamesWon() != getGamesWon()) {
			return other.getGamesWon().compareTo(getGamesWon());
		}
		if (other.getGoalDiff() != getGoalDiff()) {
			return other.getGoalDiff().compareTo(getGoalDiff());
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id != other.id)
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		return true;
	}
	
}
