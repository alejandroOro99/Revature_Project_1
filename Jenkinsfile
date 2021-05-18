pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh "chmod +x ./jenkins/workspace/test-pipeline_main"
                sh './gradlew clean build'
            }
        }
    }
}
