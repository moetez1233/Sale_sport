pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout your source code from your version control system (e.g., Git)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build your Spring Boot application
              sh 'java -jar target/app-0.0.1-SNAPSHOT.jar' // Adjust the path to your JAR file
            }
        }

        stage('Test') {
            steps {
                // Run tests if you have any
                sh './mvnw test' // Adjust this command for your project
            }
        }

        stage('Deploy') {
            steps {
                // Deploy your Spring Boot application
               echo 'Deployment successful!'
            }
        }
    }

    post {
        success {
            // This block is executed if the pipeline succeeds
            echo 'Deployment successful!'
        }
        failure {
            // This block is executed if the pipeline fails
            echo 'Deployment failed!'
        }
    }
}
