pipeline {
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
