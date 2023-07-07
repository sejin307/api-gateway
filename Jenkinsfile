node {
  stage('========== Checkout Repository ==========') {
    checkout scm
  }

  stage('========== Build Application ==========') {
      sh 'chmod +x ./gradlew'
      sh './gradlew clean build'
  }

  stage('========== Build Image ==========') {
    app = docker.build("api2-auth", ".")
  }

  stage('========== Push Image ==========') {
    docker.withRegistry('https://036240822918.dkr.ecr.ap-northeast-2.amazonaws.com/api2-auth', 'ecr:ap-northeast-2:api2-user') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }

 //Jenkins 플러그인 설치 필요.
 //AWS Steps (Jenkinsfile 에서 withAWS 사용), Pipeline Utility Steps (Jenkinsfile readJSON사용)
 //CPU 256 MEMORY 512 DEFAULT세팅
 //ECS - CLUSTER / SERVICE / TASK 변경시 아래 STAGE 변수도 변경해야함.
    stage('========== Update ECS Service ==========') {
        withAWS(credentials: 'api2-user') {
            // Register a new revision of Task Definition with the updated Docker image
            //def taskdef = sh(script: 'aws ecs register-task-definition --family "container-task" --network-mode "awsvpc" --requires-compatibilities "FARGATE" --execution-role-arn "arn:aws:iam::036240822918:role/ecsTaskExecutionRole" --cpu "256" --memory "512" --container-definitions "[{\\"name\\": \\"container-task\\",\\"image\\": \\"036240822918.dkr.ecr.ap-northeast-2.amazonaws.com/api2-auth:' + env.BUILD_NUMBER + '\\",\\"cpu\\": 256,\\"memory\\": 512,\\"essential\\": true, \\"portMappings\\": [{\\"containerPort\\": 8080, \\"protocol\\": \\"tcp\\"}], \\"logConfiguration\\": { \\"logDriver\\": \\"awslogs\\", \\"options\\": { \\"awslogs-group\\": \\"/ecs/api2-auth\\", \\"awslogs-region\\": \\"ap-northeast-2\\", \\"awslogs-stream-prefix\\": \\"ecs\\"}}}]"', returnStdout: true)
            def executionRoleArn = "arn:aws:iam::036240822918:role/ecsTaskExecutionRole"
            def cpu = "256"
            def memory = "512"
            def containerName = "container-task"
            def image = "036240822918.dkr.ecr.ap-northeast-2.amazonaws.com/api2-auth:" + env.BUILD_NUMBER
            def logGroup = "/ecs/api2-auth"
            def region = "ap-northeast-2"

            def taskdef = sh(script: """
                aws ecs register-task-definition --family "${containerName}" --network-mode "awsvpc" --requires-compatibilities "FARGATE" --execution-role-arn "${executionRoleArn}" --cpu "${cpu}" --memory "${memory}" --container-definitions "[{\\"name\\": \\"${containerName}\\",\\"image\\": \\"${image}\\",\\"cpu\\": ${cpu},\\"memory\\": ${memory},\\"essential\\": true, \\"portMappings\\": [{\\"containerPort\\": 8080, \\"protocol\\": \\"tcp\\"}], \\"logConfiguration\\": { \\"logDriver\\": \\"awslogs\\", \\"options\\": { \\"awslogs-group\\": \\"${logGroup}\\", \\"awslogs-region\\": \\"${region}\\", \\"awslogs-stream-prefix\\": \\"ecs\\"}}}]"
            """, returnStdout: true)


            // Parse the output JSON to get the new revision number
            def taskdefJson = readJSON text: taskdef
            def newRevision = taskdefJson.taskDefinition.revision

            // Update the ECS service with the new Task Definition
            //sh 'aws ecs update-service --cluster "api-auth-dev-cluster" --service "api2-auth-dev-service" --task-definition "container-task:' + newRevision + '"'

            def clusterName = "api-auth-dev-cluster"
            def serviceName = "api2-auth-dev-service"
            def taskDefinition = "container-task:" + newRevision

            sh "aws ecs update-service --cluster \"${clusterName}\" --service \"${serviceName}\" --task-definition \"${taskDefinition}\""

        }
    }
}
