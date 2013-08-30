package org.foosball

import org.foosball.Playoff;
import org.foosball.Session;
import org.springframework.dao.DataIntegrityViolationException

class PlayoffController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer id) {
		Integer sessionId = null
		if (id) {
			sessionId = id
		}
		if (!sessionId) {
			sessionId = getCurrentSession()
		}
		def playoffInstanceList = Playoff.findAll(sort:'id', order: 'desc') { sessionId == sessionId }
		playoffInstanceList
        [playoffInstanceList: playoffInstanceList, playoffInstanceTotal: Playoff.count(), sessionId: sessionId]
    }

    def create(Integer id) {
		[playoffInstance: new Playoff(params), sessionId: id]
    }

    def save() {
        def playoffInstance = new Playoff(params)
        if (!playoffInstance.save(flush: true)) {
            render(view: "create", model: [playoffInstance: playoffInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'playoff.label', default: 'Playoff'), playoffInstance.id])
        redirect(action: "show", id: playoffInstance.id)
    }

    def show(Long id) {
        def playoffInstance = Playoff.get(id)
        if (!playoffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "list")
            return
        }

        [playoffInstance: playoffInstance]
    }

    def edit(Long id) {
        def playoffInstance = Playoff.get(id)
        if (!playoffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "list", params: [sessionId: playoffInstance.session.id])
            return
        }

        [playoffInstance: playoffInstance]
    }

    def update(Long id, Long version) {
        def playoffInstance = Playoff.get(id)
        if (!playoffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "list", id: playoffInstance.session.id)
            return
        }

        if (version != null) {
            if (playoffInstance.version > version) {
                playoffInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'playoff.label', default: 'Playoff')] as Object[],
                          "Another user has updated this Playoff while you were editing")
                render(view: "edit", model: [playoffInstance: playoffInstance])
                return
            }
        }

        playoffInstance.properties = params

        if (!playoffInstance.save(flush: true)) {
            render(view: "edit", model: [playoffInstance: playoffInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'playoff.label', default: 'Playoff'), playoffInstance.id])
        redirect(action: "show", id: playoffInstance.id)
    }

    def delete(Long id) {
        def playoffInstance = Playoff.get(id)
        if (!playoffInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "list", id: sessionId)
            return
        }
		def sessionId = playoffInstance.session.id
        try {
            playoffInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "list", id: sessionId)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'playoff.label', default: 'Playoff'), id])
            redirect(action: "show", id: id)
        }
    }
	
	private Integer getCurrentSession() {
		Session.createCriteria().get {
			projections {
				max "id"
			}
		} as Integer
	}
	
}
