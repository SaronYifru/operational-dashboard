grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        mavenLocal()
//        grailsCentral()
//        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://repo1.maven.org/maven2/"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.grails.org/"
        mavenRepo "http://repo.grails.org/grails/plugins"

        /*       grailsPlugins()
               grailsHome()
               mavenLocal()
               grailsCentral()
               mavenCentral()
               // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
               //mavenRepo "http://repository.codehaus.org"
               //mavenRepo "http://download.java.net/maven/2/"
               //mavenRepo "http://repository.jboss.com/maven2/"
        */   }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
        //runtime 'org.mongodb:mongo-java-driver:3.0.2'
        //runtime 'org.mongodb:mongo-java-driver:2.10.0'
        compile 'javax.mail:mail:1.5.0-b01'
        compile 'org.apache.commons:commons-csv:1.1'

        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
        compile 'org.grails:grails-datastore-gorm:3.1.4.RELEASE'
        compile 'org.grails:grails-datastore-core:3.1.4.RELEASE'
        test 'org.grails:grails-datastore-simple:3.1.4.RELEASE'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55.2" // or ":tomcat:8.0.20"

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.1.5"
       // compile ":excel-import:1.0.0"
        compile ':mongodb:3.0.3'
        compile ":executor:0.3"
        compile ":mongodb-create-drop:1.0.2"
        // plugins needed at runtime but not for compilation
     // runtime ":hibernate4:4.3.8.1" // or ":hibernate:3.6.10.18"
//        compile ':hibernate:3.6.10.12'
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        compile ":jquery-ui:1.10.4"
        compile ":jprogress:0.2"
        runtime ":angularjs-resources:1.4.2"
        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.9.0"
        //compile ":less-asset-pipeline:1.10.0"
        //compile ":coffee-asset-pipeline:1.8.0"
        //compile ":handlebars-asset-pipeline:1.3.0.3"
        build ":jbossas:1.0"
        compile ":uploadr:1.0.0"
    }
}
grails.project.fork = [
        test: false,
        run: false
]