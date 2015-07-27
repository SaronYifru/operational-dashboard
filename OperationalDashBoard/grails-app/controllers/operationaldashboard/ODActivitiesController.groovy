package operationaldashboard

import grails.converters.JSON
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.commons.io.monitor.FileEntry
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import java.nio.charset.Charset

class ODActivitiesController {
    def index() {
        def activities = ODActivities.list()
        def requestType = ODRequestType.list()
        [activities:activities, requestType:requestType, initialFilter:null]
    }
    def getCustomersActivities() {
        ODCustomer customer = ODCustomer.findById(params.id)
        String env = params.env
        def activities
        if (env.equals("Unknown")) {
            activities = ODActivities.findAllByCustomerAndEnvIsNull(customer)

        }
        else {
            activities = ODActivities.findAllByCustomerAndEnvLike(customer,params.env)
        }

        render(view: "index", model: [activities:activities, initialFilter:"Customer: " + customer.name])
    }
    def getTicket() {
        ODActivities activity = ODActivities.findByTicketID(params.id)
        render(view: "index", model: [activities: activity != null ? [activity] : []])

    }
    def getTicketsByRequestType() {
        ODRequestType requestType = ODRequestType.findById(params.id)
        ODThreshold threshold = ODThreshold.findByType(requestType)
        def activities = ODActivities.findAllByRequestTypeAndNumberOfDaysOpenGreaterThanEquals(requestType, threshold.thresholdValue)
        render (view: "index", model: [activities: activities, initialFilter:"Request Type: " + requestType.name])
    }
    def doUpload() {
        def file = params.actFile
        if (file.empty) {
            flash.message = 'File cannot be empty!'
            render(template: '../settings/iframeStatus')
            return
        }
        def webrootDir = servletContext.getRealPath("/") //app directory
        File fileDest = grailsApplication.mainContext.getResource("data/lsps_activities.csv").file
        file.transferTo(fileDest)
        flash.message = 'File Successfully Uploaded!'
        render(template: "../settings/iframeStatus")


    }

}
