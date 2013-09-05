package org.foosball

import org.foosball.Result;
import org.springframework.dao.DataIntegrityViolationException;
import grails.plugins.springsecurity.Secured;

class ResultController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

	def list(Integer id) {
		Integer sessionId = Session.getCurrentSession()
		if (id) {
			sessionId = id
		}
		def resultInstanceList = Result.findAll(sort:'id', order: 'desc') { sessionId == sessionId }
        [resultInstanceList: resultInstanceList, resultInstanceTotal: Result.count(), sessionList: Session.list(), sessionId: sessionId]
	}
	
	@Secured(['ROLE_USER'])
    def create(Integer id) {
		[resultInstance: new Result(params), sessionId: id]
    }
	
	@Secured(['ROLE_USER'])
    def save() {
        def resultInstance = new Result(params)
        if (!resultInstance.save(flush: true)) {
            render(view: "create", model: [resultInstance: resultInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'result.label', default: 'Result'), resultInstance.id])
        redirect(action: "show", id: resultInstance.id)
    }
	
    def show(Long id) {
        def resultInstance = Result.get(id)
        if (!resultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'result.label', default: 'Result'), id])
            redirect(action: "list")
            return
        }

        [resultInstance: resultInstance]
    }
	
	@Secured(['ROLE_USER'])
    def edit(Long id) {
        def resultInstance = Result.get(id)
        if (!resultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'result.label', default: 'Result'), id])
            redirect(action: "list")
            return
        }

        [resultInstance: resultInstance]
    }
	
	@Secured(['ROLE_USER'])
    def update(Long id, Long version) {
        def resultInstance = Result.get(id)
        if (!resultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'result.label', default: 'Result'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (resultInstance.version > version) {
                resultInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'result.label', default: 'Result')] as Object[],
                          "Another user has updated this Result while you were editing")
                render(view: "edit", model: [resultInstance: resultInstance])
                return
            }
        }

        resultInstance.properties = params

        if (!resultInstance.save(flush: true)) {
            render(view: "edit", model: [resultInstance: resultInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'result.label', default: 'Result'), resultInstance.id])
        redirect(action: "show", id: resultInstance.id)
    }
	
	@Secured(['ROLE_USER'])
    def delete(Long id) {
        def resultInstance = Result.get(id)
        if (!resultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'result.label', default: 'Result'), id])
            redirect(action: "list")
            return
        }

        try {
            resultInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'result.label', default: 'Result'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'result.label', default: 'Result'), id])
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