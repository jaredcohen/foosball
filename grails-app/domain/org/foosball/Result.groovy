package org.foosball

class Result {

	int id;
	Team winner;
	Team opponent;
	Integer sessionId;
	
	static transients = [ 'description', 'gamesWonByWinner', 'gamesWonByLoser', 'goalsForWinner', 'goalsForLoser', 'goalDiff' ]
	
	static constraints = {
		winner validator: { winner, instance ->
			if (winner.id == instance.opponent.id) {
				return 'winner and opponent must be different teams';
			}
			if (winner.session != instance.sessionId) {
				return 'invalid session value for winner';
			}
		}
		opponent validator: { opponent, instance ->
			if (opponent.id == instance.winner.id) {
				return 'winner and opponent must be different teams';
			}
			if (opponent.session != instance.sessionId) {
				return 'invalid session value for opponent';
			}
		}
	}
	
	static hasMany = [ games: Game ]
	
	static mapping = {
		games sort:'id', order:'asc';
	}
	
	String getDescription() {
		winner.name + " vs. " + opponent.name;
	}
	
	Integer getGamesWonByWinner() {
		Integer gamesWonByWinner = 0;
		games.each { game ->
			if (winner.equals(game.winner)) {
				gamesWonByWinner += 1;
			}
		}
		return gamesWonByWinner
	}
	
	Integer getGamesWonByLoser() {
		Integer gamesWonByLoser = 0;
		games.each { game ->
			if (opponent.equals(game.winner)) {
				gamesWonByLoser += 1;
			}
		}
		return gamesWonByLoser;
	}
	
	Integer getGoalsForWinner() {
		Integer goalsForWinner = 0;
		games.each { game ->
			if (winner.equals(game.winner)) {
				goalsForWinner += game.winnerScore;
			} else if (winner.equals(game.opponent)) {
				goalsForWinner += game.opponentScore;
			}
		}
		return goalsForWinner;
	}
	
	Integer getGoalsForLoser() {
		Integer goalsForLoser = 0;
		games.each { game ->
			if (opponent.equals(game.winner)) {
				goalsForLoser += game.winnerScore;
			} else if (opponent.equals(game.opponent)) {
				goalsForLoser += game.opponentScore;
			}
		}
		return goalsForLoser;
	}
	
	Integer getGoalDiff() {
		return getGoalsForWinner() - getGoalsForLoser();
	}
	
}
