package jprogress

import org.codehaus.groovy.grails.web.mapping.UrlCreator
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

/**
 * the taglib for our progressbar to simplify the usage in the system
 */
class JProgressTagLib implements ApplicationContextAware {

    ApplicationContext applicationContext

    //the used service
    ProgressService progressService

    /**
     * shows a progress montor
     */
    def jprogressDialog = { attr ->

        //include our script
        out << """
             <script type="text/javascript" src="${g.resource(plugin: 'jprogress', dir: 'js', file: "jprogress.js").encodeAsHTML()}"></script>
        """

        //the update interval
        def updateInterval = 250

        //used info meesage
        def infoMessage = "please wait till the operation finishes"

        //location for the info message
        def infoTarget = ""

        //location for the progress target
        def progressTarget = ""

        //the used progress id
        def progressId = ""

        //the designated trigger
        def trigger = ""

        if (attr.progressId == null) {
            throwTagError "please provide an attribute of the name 'progressId'"
        }
        else {
            progressId = attr.progressId
        }


        if (attr.trigger == null) {
            trigger = "*"
        }
        else {
            trigger = attr.trigger
        }

        out << """<div id="monitor_${progressId}">"""
        out << """<div class="ui-widget" > """
        out << """<div  style="margin-top: 20px; margin-bottom:20px;" id="info_${progressId}"></div>"""
        out << """</div> """
        out << """<div id="progress_${progressId}"></div>"""
        out << """</div>"""

        progressTarget = "progress_${progressId}"
        infoTarget = "info_${progressId}"

        //assign the message
        if (attr.message != null) {
            infoMessage = attr.message
        }

        //assign the update interval
        if (attr.interval != null) {
            updateInterval = attr.interval
        }

        //generate our transparant url mapping
        def urlMappings = applicationContext.getBean("grailsUrlMappingsHolder")
        UrlCreator mapping = urlMappings.getReverseMapping("JProgress", "ajaxRenderProgress", [id: progressId])
        def url = mapping.createURL("JProgress", "ajaxRenderProgress", [id: progressId], request.characterEncoding)

        //reset the bar just in case
        progressService.setProgressBarValue(progressId, 0)

        //write the actual progressbar
        out << """

            <script type="text/javascript">

                createJProgressBarDialog('${progressId}', '${infoTarget}', '${infoMessage}','${url}','${updateInterval}','${progressTarget}','${trigger}');

            </script>

        """
    }
    /**
     * includes the progresss progress bar
     */
    def jprogress = { attr ->

        //include our script
        out << """
             <script type="text/javascript" src="${g.resource(plugin: 'jprogress', dir: 'js', file: "jprogress.js").encodeAsHTML()}"></script>
        """

        //the update interval
        def updateInterval = 250

        //used info meesage
        def infoMessage = "please wait"

        //location for the info message
        def infoTarget = ""

        //location for the progress target
        def progressTarget = ""

        //the used progress id
        def progressId = ""

        //the designated trigger
        def trigger = ""

        if (attr.progressId == null) {
            throwTagError "please provide an attribute of the name 'progressId'"
        }
        else {
            progressId = attr.progressId
        }


        if (attr.trigger == null) {
            trigger = "*"
        }
        else {
            trigger = attr.trigger
        }
        /**
         * generate the progress on the fly
         */
        if (attr.progressTarget == null) {
            //no target provided, create one on the fly
            out << """<div style="width:100%;" > """
            out << """<div id="progress_${progressId}"></div>"""
            out << """</div> """


            progressTarget = "progress_${progressId}"
        }
        /**
         * use the existing one
         */
        else {
            progressTarget = attr.progressTarget
        }

        /**
         * generate an info target on the fly
         */
        if (attr.infoTarget == null) {
            //no target provided, create one on the fly
            out << """<div id="info_${progressId}"></div>"""
            infoTarget = "info_${progressId}"
        }
        /**
         * uses the existing one
         */
        else {
            infoTarget = attr.infoTarget
        }

        //assign the message
        if (attr.message != null) {
            infoMessage = attr.message
        }

        //assign the update interval
        if (attr.interval != null) {
            updateInterval = attr.interval
        }

        //generate our transparant url mapping
        def urlMappings = applicationContext.getBean("grailsUrlMappingsHolder")
        UrlCreator mapping = urlMappings.getReverseMapping("JProgress", "ajaxRenderProgress", [id: progressId])
        def url = mapping.createURL("JProgress", "ajaxRenderProgress", [id: progressId], request.characterEncoding)

        //reset the bar just in case
        progressService.setProgressBarValue(progressId, 0)

        //write the actual progressbar
        out << """

            <script type="text/javascript">

                createJProgressBar('${progressId}', '${infoTarget}', '${infoMessage}','${url}','${updateInterval}','${progressTarget}','${trigger}');

            </script>

        """
    }
}
