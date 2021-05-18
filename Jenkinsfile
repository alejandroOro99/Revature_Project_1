pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh '/var/lib/jenkins/workspace/test-pipeline_main/gradlew clean build'
            }
        }
    }
}
