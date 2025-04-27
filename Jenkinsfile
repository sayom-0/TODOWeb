pipeline {
    agent any

    environment {
        MAVEN_HOME = 'C:\\Program Files\\Apache\\Maven'
        PATH = "${env.PATH};C:\\Program Files\\Apache\\Maven\\bin"
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['staging', 'production'], description: 'Choose the environment to deploy to')
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

                        bat 'mvn clean install -U'
                        bat 'mvn clean package'
                        bat 'mvn test'
                    } catch (err) {
                        echo "Build or Test failed on latest commit. Attempting previous commit..."

                        bat 'git reset --hard HEAD~1'
                        def previousCommit = bat(script: 'git rev-parse HEAD', returnStdout: true).trim()
                        echo "Trying previous commit: ${previousCommit}"

                        try {
                            bat 'mvn clean install -U'
                            bat 'mvn clean package'
                            bat 'mvn test'
                        } catch (err2) {
                            echo "Previous commit also failed!"
                            error("Both latest and previous commits failed.")
                        }
                    }
                }
            }
        }

        stage('Build Docker Image') {
                    steps {
                        script {
                            // Ensure that ENVIRONMENT is correctly defined and sanitized
                            def sanitizedEnv = params.ENVIRONMENT.toLowerCase().replaceAll("[^a-z0-9-]", "-")
                            def tag = "semstatestudentdocker/todoweb-app:${sanitizedEnv}"

                            echo "Tagging the Docker image with ${tag}"

                            // Tag the Docker image
                            bat "docker tag todoweb-app ${tag}"
                        }
                    }
                }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    bat 'docker tag todoweb-app semstatestudentdocker/todoweb-app:${params.ENVIRONMENT}'
                    bat 'docker push semstatestudentdocker/todoweb-app:${params.ENVIRONMENT}'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    if (params.ENVIRONMENT == 'staging') {
                        echo "Deploying to Staging..."
                        withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                            bat 'docker run -d -p 8079:8079 semstatestudentdocker/todoweb-app:staging'
                        }
                    } else if (params.ENVIRONMENT == 'production') {
                        echo "Deploying to Production..."
                        withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                            bat 'docker run -d -p 8080:8080 semstatestudentdocker/todoweb-app:production'
                        }
                    }
                }
            }
        }
    }
}
