pipeline {
    agent any

    environment {
    MAVEN_HOME = 'C:\\Program Files\\Apache\\Maven'
    PATH = "${env.PATH};C:\\Program Files\\Apache\\Maven\\bin"
    }

    stages {
        stage('Maven Dependency Update') {
            steps {
                bat 'mvn clean install -U'
            }
        }
        stage('Checkout') {
            steps {
                git 'https://github.com/Sayom-0/TODOWeb.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t todoweb-app .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    bat 'docker tag todoweb-app semstatestudentdocker/todoweb-app:latest'
                    bat 'docker push semstatestudentdocker/todoweb-app:latest'
                }
            }
        }

        stage('Deploy') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    bat 'docker run -d -p 8080:8080 semstatestudentdocker/todoweb-app:latest'
                }
            }
        }

    }
}
