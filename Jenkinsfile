pipeline {
    agent any

    environment {
        MAVEN_HOME = 'C:\\Program Files\\Apache\\Maven'
        PATH = "${env.PATH};C:\\Program Files\\Apache\\Maven\\bin"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Sayom-0/TODOWeb.git'
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    def currentCommit = ''
                    try {
                        currentCommit = bat(script: 'git rev-parse HEAD', returnStdout: true).trim()

                        bat 'mvn clean install -U'     // Moved here under protection
                        bat 'mvn clean package'
                        bat 'mvn test'
                    } catch (err) {
                        echo "Build or Test failed on latest commit. Attempting previous commit..."

                        bat 'git checkout HEAD^'
                        def previousCommit = bat(script: 'git rev-parse HEAD', returnStdout: true).trim()
                        echo "Trying previous commit: ${previousCommit}"

                        try {
                            bat 'mvn clean install -U'
                            bat 'mvn clean package'
                            bat 'mvn test'
                        } catch (err2) {
                            echo "Previous commit also failed!"
                            error("Both latest and previous commits failed.")
                        } finally {
                            // Always restore original commit
                            bat "git checkout ${currentCommit}"
                        }
                    }
                }
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
