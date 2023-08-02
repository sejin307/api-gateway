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
    docker.withRegistry('https://025272456049.dkr.ecr.ap-northeast-2.amazonaws.com/api-gw', 'ecr:ap-northeast-2:API-GW') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }

 //Jenkins 플러그인 설치 필요.
 //AWS Steps (Jenkinsfile 에서 withAWS 사용), Pipeline Utility Steps (Jenkinsfile readJSON사용)
 //CPU 256 MEMORY 512 DEFAULT세팅
 //ECS - CLUSTER / SERVICE / TASK 변경시 아래 STAGE 변수도 변경해야함.
     stage('========== Update ECS Service ==========') {
        withAWS(credentials: 'API-GW') {
            // Register a new revision of Task Definition with the updated Docker image
            //def taskdef = sh(script: 'aws ecs register-task-definition --family "container-task" --network-mode "awsvpc" --requires-compatibilities "FARGATE" --execution-role-arn "arn:aws:iam::036240822918:role/ecsTaskExecutionRole" --cpu "256" --memory "512" --container-definitions "[{\\"name\\": \\"container-task\\",\\"image\\": \\"036240822918.dkr.ecr.ap-northeast-2.amazonaws.com/api2-auth:' + env.BUILD_NUMBER + '\\",\\"cpu\\": 256,\\"memory\\": 512,\\"essential\\": true, \\"portMappings\\": [{\\"containerPort\\": 8080, \\"protocol\\": \\"tcp\\"}], \\"logConfiguration\\": { \\"logDriver\\": \\"awslogs\\", \\"options\\": { \\"awslogs-group\\": \\"/ecs/api2-auth\\", \\"awslogs-region\\": \\"ap-northeast-2\\", \\"awslogs-stream-prefix\\": \\"ecs\\"}}}]"', returnStdout: true)
            def executionRoleArn = "arn:aws:iam::025272456049:role/ecsTaskExecutionRole"
            def cpu = "1024"
            def memory = "3072"
            def containerName = "API-GW-CONTAINER"
            def image = "025272456049.dkr.ecr.ap-northeast-2.amazonaws.com/api-gw:" + env.BUILD_NUMBER
            def logGroup = "/ecs/api-gw"
            def region = "ap-northeast-2"

            def taskdef = sh(script: """
                aws ecs register-task-definition --family "${containerName}" --network-mode "awsvpc" --requires-compatibilities "FARGATE" --execution-role-arn "${executionRoleArn}" --cpu "${cpu}" --memory "${memory}" --container-definitions "[{\\"name\\": \\"${containerName}\\",\\"image\\": \\"${image}\\",\\"cpu\\": ${cpu},\\"memory\\": ${memory},\\"essential\\": true, \\"portMappings\\": [{\\"containerPort\\": 8080, \\"protocol\\": \\"tcp\\"}], \\"logConfiguration\\": { \\"logDriver\\": \\"awslogs\\", \\"options\\": { \\"awslogs-group\\": \\"${logGroup}\\", \\"awslogs-region\\": \\"${region}\\", \\"awslogs-stream-prefix\\": \\"ecs\\"}}}]"
            """, returnStdout: true)


            // Parse the output JSON to get the new revision number
            def taskdefJson = readJSON text: taskdef
            def newRevision = taskdefJson.taskDefinition.revision

            def clusterName = "API-GW-CLUSTER"
            def serviceName = "CONTAINER-SERVICE"
            def taskDefinition = "API-GW-CONTAINER:" + newRevision //이게 왜 컨테이너명인지..?????

            sh "aws ecs update-service --cluster \"${clusterName}\" --service \"${serviceName}\" --task-definition \"${taskDefinition}\""

        }
    }
}
