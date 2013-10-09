package org.foosball

class Session {

	int id;
	String description;

	static transients = [
		'currentSession',
		'numberOfTeams',
		'finalPlayoffRoundNum'
	]

	static mapping = {
		id generator: 'assigned'
	}
	
	static constraints = {
	}
	
	public static Integer getCurrentSession() {
		Session.createCriteria().get { 
			projections { 
				max "id" 
			}
		} as Integer
	}
	
	Integer getNumberOfTeams() {
		Integer numberOfTeams = Team.createCriteria().get {
			eq("session", id)
			projections {
				rowCount
			}
		} as Integer
	}
	
	Integer getFinalPlayoffRoundNum() {
		Integer roundNum = Playoff.createCriteria().get {
			eq("sessionId", id)
			projections {
				max "roundNum"
			}
		} as Integer
	}
}
