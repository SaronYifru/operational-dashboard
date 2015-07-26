package operationaldashboard

import grails.rest.Resource
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

import java.nio.charset.Charset
@Resource(uri='/customers')
class ODCustomerController {
    File customersCSV
    List<CSVRecord> customers
    def index() {}
   def setFocusFlag() {
       ODCustomer customer = ODCustomer.findById(params.id)
       String focus = params.customerFocusFlag
       customer.setFocusFlag(focus.equals("true"))
       customer.save()
       log.info(focus)
       render(status: 200, text: 'HTTP status 200 OK')
   }
    def addCustomer() {
        ODCustomer customer = new ODCustomer(name:params.customerName, focusFlag: params.focusFlag.equals("on")?true:false).save()
        render template: "customerTableRows", model: [customers:[customer]]
    }
}
