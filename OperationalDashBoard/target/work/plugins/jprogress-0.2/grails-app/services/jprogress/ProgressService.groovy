package jprogress


import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpSession

/**
 * internal service todo the actual work and store the value in a map
 */
class ProgressService {

    static transactional = false

    /**
     * sets the
     * @param id
     * @param value
     * @return
     */
    def setProgressBarValue(String id, double value) {
        getSession().putValue(id, value)
    }

    /**
     * retrieves the value of the progress bar
     * @param id
     * @return
     */
    def retrieveProgressBarValue(String id) {

        if (getSession().getValue(id) == null) {
            getSession().putValue(id, 0)
        }

        return getSession().getValue(id)
    }

    /**
     * check if the progressbar is complete
     * @param id
     * @return
     */
    boolean isProgressBarComplete(String id) {

        if (getSession().getValue(id) != null) {
            def value = getSession().getValue(id)

            if (value instanceof Double) {
                return value >= 100
            }

        }

        return false
    }


    private HttpSession getSession() {
        return RequestContextHolder.currentRequestAttributes().getSession()
    }

}
