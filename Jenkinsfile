pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh "chmod +x ./jenkins/script.sh"
                sh './gradlew clean build'
            }
        }
    }
}
