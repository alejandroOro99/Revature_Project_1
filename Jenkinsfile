node{
    def buildNum = BUILD_NUMBER
    stage("Build app"){
        sh './gradlew clean build'
    }s
    
    stage("build docker image"){
        sh "docker build -t alejandrooro99/project1:${buildNum} ."
        
    }
    stage("Docker login and Push"){
        withCredentials([string(credentialsId: 'Docker_Hub_Pwd', variable: 'Docker_Hub_Pwd')]) {
            sh "docker login -u alejandrooro99 -p ${Docker_Hub_Pwd}"
        }
        
        sh "docker push alejandrooro99/project1:${buildNum}"
        
    }
    
    stage("Deploy app"){
        sshagent(['Docker_Dev_Server_SSH']) {
            sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.40.4 docker rm -f project1container || true"
            sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.40.4 docker run -t -dp 9000:80 --name project1container alejandrooro99/project1:${buildNum}"
        }
        
    }
}
