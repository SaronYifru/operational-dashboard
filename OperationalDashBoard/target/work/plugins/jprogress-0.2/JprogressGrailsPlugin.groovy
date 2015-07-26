class JprogressGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.5 > *"
    // the other plugins this plugin depends on
    def dependsOn = [
        jquery:'1.4.3.2 > *',
        jqueryUi:'1.8.6.1 > *'

    ]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Gert Wohlgemuth"
    def authorEmail = "berlinguyinca@gmail.com"
    def title = "This plugin provides a simple to use Progressbar, based on JQueryUI"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/jprogress"
}
