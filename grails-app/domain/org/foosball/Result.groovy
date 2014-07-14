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
		}
		opponent validator: { opponent, instance ->
			if (opponent.id == instance.winner.id) {
				return 'winner and opponent must be different teams';
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
			if (winner.id.equals(game.winner.id)) {
				gamesWonByWinner += 1;
			}
		}
		return gamesWonByWinner;
	}
	
	Integer getGamesWonByLoser() {
		Integer gamesWonByLoser = 0;
		games.each { game ->
			if (opponent.id.equals(game.winner.id)) {
				gamesWonByLoser += 1;
			}
		}
		return gamesWonByLoser;
	}
	
	Integer getGoalsForWinner() {
		Integer goalsForWinner = 0;
		games.each { game ->
			if (winner.id.equals(game.winner.id)) {
				goalsForWinner += game.winnerScore;
			} else if (winner.id.equals(game.opponent.id)) {
				goalsForWinner += game.opponentScore;
			}
		}
		return goalsForWinner;
	}
	
	Integer getGoalsForLoser() {
		Integer goalsForLoser = 0;
		games.each { game ->
			if (opponent.id.equals(game.winner.id)) {
				goalsForLoser += game.winnerScore;
			} else if (opponent.id.equals(game.opponent.id)) {
				goalsForLoser += game.opponentScore;
			}
		}
		return goalsForLoser;
	}
	
	Integer getGoalDiff() {
		return getGoalsForWinner() - getGoalsForLoser();
	}
	
}
