package org.foosball

import org.foosball.Game;
import org.springframework.dao.DataIntegrityViolationException;
import grails.plugins.springsecurity.Secured;

class GameController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [gameInstanceList: Game.list(params), gameInstanceTotal: Game.count()]
    }

	@Secured(['ROLE_USER'])
    def create() {
		[gameInstance: new Game(params)]
    }

	@Secured(['ROLE_USER'])
    def save() {
        def gameInstance = new Game(params)
        if (!gameInstance.save(flush: true)) {
            render(view: "create", model: [gameInstance: gameInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'game.label', default: 'Game'), gameInstance.id])
        redirect(controller: "result", action: "show", id: gameInstance.result.id)
    }

    def show(Long id) {
        def gameInstance = Game.get(id)
        if (!gameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "list")
            return
        }

        [gameInstance: gameInstance]
    }

	@Secured(['ROLE_USER'])
    def edit(Long id) {
        def gameInstance = Game.get(id)
        if (!gameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "list")
            return
        }

        [gameInstance: gameInstance, resultInstance: gameInstance.result]
    }

	@Secured(['ROLE_USER'])
    def update(Long id, Long version) {
        def gameInstance = Game.get(id)
        if (!gameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (gameInstance.version > version) {
                gameInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'game.label', default: 'Game')] as Object[],
                          "Another user has updated this Game while you were editing")
                render(view: "edit", model: [gameInstance: gameInstance])
                return
            }
        }

        gameInstance.properties = params

        if (!gameInstance.save(flush: true)) {
            render(view: "edit", model: [gameInstance: gameInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'game.label', default: 'Game'), gameInstance.id])
        redirect(controller: "result", action: "show", id: gameInstance.result.id)
    }

	@Secured(['ROLE_USER'])
    def delete(Long id) {
        def gameInstance = Game.get(id)
        if (!gameInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "list")
            return
        }

        try {
            gameInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'game.label', default: 'Game'), id])
            redirect(action: "show", id: id)
        }
    }
}
