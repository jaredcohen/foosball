package org.foosball

import org.foosball.PlayoffGame;
import org.springframework.dao.DataIntegrityViolationException;
import grails.plugins.springsecurity.Secured;

class PlayoffGameController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [playoffGameInstanceList: PlayoffGame.list(params), playoffGameInstanceTotal: PlayoffGame.count()]
    }

	@Secured(['ROLE_USER'])
    def create() {
        [playoffGameInstance: new PlayoffGame(params)]
    }
	
	@Secured(['ROLE_USER'])
    def save() {
        def playoffGameInstance = new PlayoffGame(params)
        if (!playoffGameInstance.save(flush: true)) {
            render(view: "create", model: [playoffGameInstance: playoffGameInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), playoffGameInstance.id])
        redirect(controller: "playoff", action: "show", id: playoffGameInstance.result.id)
    }

    def show(Long id) {
        def playoffGameInstance = PlayoffGame.get(id)
        if (!playoffGameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "list")
            return
        }

        [playoffGameInstance: playoffGameInstance]
    }
	
	@Secured(['ROLE_USER'])
    def edit(Long id) {
        def playoffGameInstance = PlayoffGame.get(id)
        if (!playoffGameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "list")
            return
        }

        [playoffGameInstance: playoffGameInstance]
    }
	
	@Secured(['ROLE_USER'])
    def update(Long id, Long version) {
        def playoffGameInstance = PlayoffGame.get(id)
        if (!playoffGameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (playoffGameInstance.version > version) {
                playoffGameInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'playoffGame.label', default: 'PlayoffGame')] as Object[],
                          "Another user has updated this PlayoffGame while you were editing")
                render(view: "edit", model: [playoffGameInstance: playoffGameInstance])
                return
            }
        }

        playoffGameInstance.properties = params

        if (!playoffGameInstance.save(flush: true)) {
            render(view: "edit", model: [playoffGameInstance: playoffGameInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), playoffGameInstance.id])
        redirect(controller: "playoff", action: "show", id: playoffGameInstance.result.id)
    }
	
	@Secured(['ROLE_USER'])
    def delete(Long id) {
        def playoffGameInstance = PlayoffGame.get(id)
        if (!playoffGameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "list")
            return
        }

        try {
            playoffGameInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'playoffGame.label', default: 'PlayoffGame'), id])
            redirect(action: "show", id: id)
        }
    }
}
