package org.foosball

class Person implements Comparable {

	int id;
	String name;
	boolean active;

	static belongsTo = org.foosball.Team;

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
		'totalGames',
		'avgGoalsScoredPerGame',
		'avgGoalsAgainstPerGame',
		'avgGoalDiff',
		'avgFinalPosition',
		'playerRating'
	]

	static constraints = {
	}

	static hasMany = [ teams: Team ]

	static mapping = { teams joinTable: [name: 'TEAM_MEMBER', column: 'ID', key: 'MEMBER_ID'] }

	Integer getWinCount() {
		Integer wins = 0;
		teams.each { team ->
			wins += team.getWinCount();
			wins += team.getPlayoffWinCount();
		}
		return wins;
	}

	Integer getLossCount() {
		Integer losses = 0;
		teams.each { team ->
			losses += team.getLossCount();
			losses += team.getPlayoffLossCount();
		}
		return losses;
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
		teams.each { team ->
			gamesWon += team.getGamesWon();
			gamesWon += team.getPlayoffGamesWon();
		}
		return gamesWon;
	}

	Integer getGamesLost() {
		Integer gamesLost = 0;
		teams.each { team ->
			gamesLost += team.getGamesLost();
			gamesLost += team.getPlayoffGamesLost();
		}
		return gamesLost;
	}

	Integer getTotalGames() {
		return getGamesWon() + getGamesLost();
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
		teams.each { team ->
			goalsFor += team.getGoalsFor();
			goalsFor += team.getPlayoffGoalsFor();
		}
		return goalsFor;
	}

	Integer getGoalsAgainst() {
		Integer goalsAgainst = 0;
		teams.each { team ->
			goalsAgainst += team.getGoalsAgainst();
			goalsAgainst += team.getPlayoffGoalsAgainst();
		}
		return goalsAgainst;
	}

	Double getAvgGoalsScoredPerGame() {
		Double avgGoalsScoredPerGame = new Double("0.0");
		if (getGoalsFor() && getTotalGames()) {
			avgGoalsScoredPerGame = getGoalsFor() / getTotalGames();
		}
		avgGoalsScoredPerGame.round(2);
	}

	Double getAvgGoalsAgainstPerGame() {
		Double avgGoalsAgainstPerGame = new Double("0.0");
		if (getGoalsAgainst() && getTotalGames()) {
			avgGoalsAgainstPerGame = getGoalsAgainst() / getTotalGames();
		}
		avgGoalsAgainstPerGame.round(2);
	}

	Integer getGoalDiff() {
		return getGoalsFor() - getGoalsAgainst();
	}
	
	Double getAvgGoalDiff() {
		Double avgGoalDiff = getAvgGoalsScoredPerGame() - getAvgGoalsAgainstPerGame();
		return avgGoalDiff.round(2);
	}
	
	Double getPlayerRating() {
		//start with winning percentage
		Double playerRating = (getGamesWinPct() / 100) + (getWinPct() / 100);
		//add 0.1 for each match played to account for players who've played less matches
		playerRating += (getTotalGames() * 0.02);
		//add average goal difference * 0.1
		playerRating += ((getAvgGoalDiff()) * 0.25);
		
		teams.each { team ->
			if (team.getFinalTeamPosition()) {
				Double teamPositionRating = ((team.getFinalTeamPositionRating()) / ((Session.getCurrentSession() + 1) - team.getSessionId()));
				playerRating += teamPositionRating;
			}
		}
		
		return ((playerRating * 10) + 50).round(2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		def other = (Person) o;
		if (other.getPlayerRating() != getPlayerRating()) {
			return other.getPlayerRating().compareTo(getPlayerRating());
		}
		return 0;
	}
}
