pipeline {
    agent any

    tools {
        maven "maven"
        jdk "jdk-11"
    }

    stages {
        stage('Clone with GitLab') {
            steps {
                git branch: 'main', credentialsId: 'loginAndPasword', url: 'https://git.foxminded.com.ua/foxstudent100613/masteruniversity.git'
            }
        }

        stage('SonarQube analysis') {
            steps {
                script {
                    withSonarQubeEnv() {
                        bat "mvn clean verify sonar:sonar -Dsonar.projectKey=test_Project"
                        }
                }
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean install -e"
            }
        }

        stage('Docker') {
            steps {
                bat "docker build -t schedule ."

                bat "docker-compose up"
            }
        }
    }
}
