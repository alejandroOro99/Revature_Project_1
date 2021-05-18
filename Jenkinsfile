
pipeline {
    env.JENKINS_NODE_COOKIE = 'dontKillMe' // this is necessary for the Gradle daemon to be kept alive
    agent any
    stages {
        stage('build') {
            steps {
                withGradle{
                    sh ' chmod +x ./gradlew'
                    sh './gradlew clean build'
                }
                
            }
        }
    }
}
