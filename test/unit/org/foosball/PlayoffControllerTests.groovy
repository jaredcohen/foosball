package org.foosball



import org.foosball.Playoff;
import org.foosball.PlayoffController;
import org.junit.*
import grails.test.mixin.*

@TestFor(PlayoffController)
@Mock(Playoff)
class PlayoffControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/playoff/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.playoffInstanceList.size() == 0
        assert model.playoffInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.playoffInstance != null
    }

    void testSave() {
        controller.save()

        assert model.playoffInstance != null
        assert view == '/playoff/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/playoff/show/1'
        assert controller.flash.message != null
        assert Playoff.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/playoff/list'

        populateValidParams(params)
        def playoff = new Playoff(params)

        assert playoff.save() != null

        params.id = playoff.id

        def model = controller.show()

        assert model.playoffInstance == playoff
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/playoff/list'

        populateValidParams(params)
        def playoff = new Playoff(params)

        assert playoff.save() != null

        params.id = playoff.id

        def model = controller.edit()

        assert model.playoffInstance == playoff
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/playoff/list'

        response.reset()

        populateValidParams(params)
        def playoff = new Playoff(params)

        assert playoff.save() != null

        // test invalid parameters in update
        params.id = playoff.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/playoff/edit"
        assert model.playoffInstance != null

        playoff.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/playoff/show/$playoff.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        playoff.clearErrors()

        populateValidParams(params)
        params.id = playoff.id
        params.version = -1
        controller.update()

        assert view == "/playoff/edit"
        assert model.playoffInstance != null
        assert model.playoffInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/playoff/list'

        response.reset()

        populateValidParams(params)
        def playoff = new Playoff(params)

        assert playoff.save() != null
        assert Playoff.count() == 1

        params.id = playoff.id

        controller.delete()

        assert Playoff.count() == 0
        assert Playoff.get(playoff.id) == null
        assert response.redirectedUrl == '/playoff/list'
    }
}
