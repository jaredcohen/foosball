package org.foosball

class Team implements Comparable {

    int id;
	String name;
	Integer session;
	
	static transients = ['winCount', 'lossCount', 'winPct', 'gamesWon', 'gamesLost', 'gamesWinPct', 'goalsFor', 'goalsAgainst', 'goalDiff', 
		'playoffWinCount', 'playoffLossCount', 'playoffGamesWon', 'playoffGamesLost', 'playoffGoalsFor', 'playoffGoalsAgainst']
	
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
}
