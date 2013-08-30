package org.foosball

class PlayoffGame {

	int id;
	Playoff result;
	Team winner;
	Team opponent;
	Integer winnerScore;
	Integer opponentScore;
	Integer sessionId;
	
    static constraints = {
		winnerScore max: 10;
		opponentScore max: 9;
		winner validator: { winner, instance ->
			if (winner.id != instance.result.winner.id && winner.id != instance.result.opponent.id) {
				return 'winner must be one of the two teams involved in this match';
			}
			if (winner.id == instance.opponent.id) {
				return 'winner and opponent must be different teams';
			}
			if (winner.session != instance.sessionId) {
				return 'invalid session value for winner';
			}
		}
		opponent validator: { opponent, instance ->
			if (opponent.id != instance.result.winner.id && opponent.id != instance.result.opponent.id) {
				return 'opponent must be one of the two teams involved in this match';
			}
			if (opponent.id == instance.winner.id) {
				return 'winner and opponent must be different teams';
			}
			if (opponent.session != instance.sessionId) {
				return 'invalid session value for opponent';
			}
		}
    }
	
}
