node {
  stage('========== Checkout Repository ==========') {
    checkout scm
  }

  stage('========== Build Application ==========') {
      sh 'chmod +x ./gradlew'
      sh './gradlew clean build'
  }

  stage('========== Build Image ==========') {
    app = docker.build("api-gw", ".")
  }

  stage('========== Push Image ==========') {
    docker.withRegistry('https://269923429649.dkr.ecr.ap-northeast-2.amazonaws.com/api-gw', 'ecr:ap-northeast-2:API-GW') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }
}
