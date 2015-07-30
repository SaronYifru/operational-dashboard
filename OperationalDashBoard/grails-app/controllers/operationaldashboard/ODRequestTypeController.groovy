package operationaldashboard

import grails.rest.Resource

@Resource(uri='/requests')
class ODRequestTypeController {

    def index() {}
    def setFocusFlag() {
        ODRequestType type = ODRequestType.findById(params.id)
        String focus = params.requestTypeFocusFlag
        type.setFocusFlag(focus.equals("true"))
        type.save()
        render(status: 200, text: 'HTTP status 200 OK')

    }
    def setThreshold() {
        log.info(params)
        ODRequestType type = ODRequestType.findById(params.name)
        type.setThresholdValue(Integer.valueOf(params.value))
        type.save()
        render(status: 200, text: 'HTTP status 200 OK')

    }
}
