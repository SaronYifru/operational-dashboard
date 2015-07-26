package operationaldashboard

import grails.converters.JSON
import grails.rest.Resource

@Resource(uri='/thresholds')
class ODThresholdController {
    def index() {}

    def setThreshold() {
        log.info(params)
        ODThreshold threshold = ODThreshold.findById(params.name)
        threshold.setThresholdValue(Integer.valueOf(params.value))
        threshold.save()
        render(status: 200, text: 'HTTP status 200 OK')

    }
}
