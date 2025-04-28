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
    agent {
        label 'linux_agents'
    }
    environment {
        AWS_ECR = '340752809566.dkr.ecr.us-east-1.amazonaws.com'
    }
    stages {
        stage('Get credentials') {
            steps {
                withCredentials([aws(credentialsId: "aws_credentials")]){
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $AWS_ECR'
                }
            }
        }
        stage('Pull image') {
            steps {
                sh 'docker pull $AWS_ECR/automations:latest'
            }
        }
        stage('Run') {
            steps {
                sh 'docker run --rm $AWS_ECR/automations:latest'
            }
        }
    }
}