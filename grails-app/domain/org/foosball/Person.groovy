package org.foosball

class Person implements Comparable {

	int id;
	String name;
	
	static belongsTo = org.foosball.Team;
	
	static transients = ['winCount', 'lossCount', 'winPct', 'gamesWon', 'gamesLost', 'gamesWinPct', 'goalsFor', 'goalsAgainst', 'goalDiff', 'totalGames', 'avgGoalsScoredPerGame', 'avgGoalsAgainstPerGame']
	
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

	@Override
	public int compareTo(Object o) {
		def other = (Person) o;
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
}
