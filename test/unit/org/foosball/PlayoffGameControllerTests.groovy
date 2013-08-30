package org.foosball



import org.foosball.PlayoffGame;
import org.foosball.PlayoffGameController;
import org.junit.*
import grails.test.mixin.*

@TestFor(PlayoffGameController)
@Mock(PlayoffGame)
class PlayoffGameControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/playoffGame/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.playoffGameInstanceList.size() == 0
        assert model.playoffGameInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.playoffGameInstance != null
    }

    void testSave() {
        controller.save()

        assert model.playoffGameInstance != null
        assert view == '/playoffGame/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/playoffGame/show/1'
        assert controller.flash.message != null
        assert PlayoffGame.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/playoffGame/list'

        populateValidParams(params)
        def playoffGame = new PlayoffGame(params)

        assert playoffGame.save() != null

        params.id = playoffGame.id

        def model = controller.show()

        assert model.playoffGameInstance == playoffGame
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/playoffGame/list'

        populateValidParams(params)
        def playoffGame = new PlayoffGame(params)

        assert playoffGame.save() != null

        params.id = playoffGame.id

        def model = controller.edit()

        assert model.playoffGameInstance == playoffGame
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/playoffGame/list'

        response.reset()

        populateValidParams(params)
        def playoffGame = new PlayoffGame(params)

        assert playoffGame.save() != null

        // test invalid parameters in update
        params.id = playoffGame.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/playoffGame/edit"
        assert model.playoffGameInstance != null

        playoffGame.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/playoffGame/show/$playoffGame.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        playoffGame.clearErrors()

        populateValidParams(params)
        params.id = playoffGame.id
        params.version = -1
        controller.update()

        assert view == "/playoffGame/edit"
        assert model.playoffGameInstance != null
        assert model.playoffGameInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/playoffGame/list'

        response.reset()

        populateValidParams(params)
        def playoffGame = new PlayoffGame(params)

        assert playoffGame.save() != null
        assert PlayoffGame.count() == 1

        params.id = playoffGame.id

        controller.delete()

        assert PlayoffGame.count() == 0
        assert PlayoffGame.get(playoffGame.id) == null
        assert response.redirectedUrl == '/playoffGame/list'
    }
}
