/*pipeline {
    agent { label 'ec2_instance' }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'sudo docker build -t wakeup-portfolio .'
            }
        }
        stage('Run') {
            steps {
                echo 'Running....'
                sh 'sudo docker run wakeup-portfolio'
            }
        }
    }
}*/
pipeline {
    agent { label 'linux_agents' }

    stages {
        stage('Configure AWS credentials') {
            steps {
                withCredentials(bindings: [certificate(credentialsId: 'aws_credentials', \
                                                       keystoreVariable: 'AWS_ACCESS_KEY', \
                                                       passwordVariable: 'SECRET_ACCESS_KEY')]){
                    echo 'good'
                }
            }
        }
        stage('Get credentials') {
            steps {
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 340752809566.dkr.ecr.us-east-1.amazonaws.com'
            }
        }
        stage('Pull image') {
            steps {
                sh 'docker pull automations:latest'
            }
        }
        stage('Run') {
            steps {
                sh 'docker run -rm automations:latest'
            }
        }
    }
}