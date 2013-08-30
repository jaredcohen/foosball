package org.foosball

import org.foosball.Playoff;
import org.foosball.Session;
import org.foosball.Team;
import org.springframework.dao.DataIntegrityViolationException

class TableController {

	def index() {
		redirect(action: "table", id: params.id)
	}

	def table(Integer id) {
		Integer sessionId = getCurrentSession()
		if (id) {
			sessionId = id
		}
		def teamInstanceList = Team.findAll { session == sessionId }
		teamInstanceList.sort()

		def finalMatchInstance = Playoff.find { 
			sessionId == sessionId
			finalMatch == true
		}
		
		if (finalMatchInstance) {
			def consolationMatchInstance = Playoff.find { 
				sessionId == sessionId
				finalMatch == false
				roundNum == finalMatchInstance.roundNum
			}
			return [teamInstanceList: teamInstanceList, teamInstanceTotal: Team.count(), sessionList: Session.list(), sessionId: sessionId, finalMatch: finalMatchInstance, consolationMatch: consolationMatchInstance]
		}
		
		[teamInstanceList: teamInstanceList, teamInstanceTotal: Team.count(), sessionList: Session.list(), sessionId: sessionId, finalMatch: finalMatchInstance]
	}

	private Integer getCurrentSession() {
		Session.createCriteria().get {
			projections { max "id" }
		} as Integer
	}
}
