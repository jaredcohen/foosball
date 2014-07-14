package org.foosball

import org.foosball.Playoff;
import org.foosball.Session;
import org.foosball.Team;
import org.foosball.SecUser;
import org.springframework.dao.DataIntegrityViolationException

class TableController {

	def index() {
		redirect(action: "table", id: params.id)
	}

	def table(Integer id) {
		Integer sessionId = Session.getCurrentSession()
		if (id >= 0) {
			sessionId = id
		}
		def teamInstanceList = Team.findAll { sessionId == sessionId }
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
			return [teamInstanceList: teamInstanceList, teamInstanceTotal: Team.count(), sessionList: Session.findAll { id != 0 }, sessionId: sessionId, finalMatch: finalMatchInstance, consolationMatch: consolationMatchInstance]
		}
		
		[teamInstanceList: teamInstanceList, teamInstanceTotal: Team.count(), sessionList: Session.findAll { id != 0 }, sessionId: sessionId, finalMatch: finalMatchInstance]
	}

}