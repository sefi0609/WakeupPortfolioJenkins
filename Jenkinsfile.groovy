pipeline {
    agent { label 'ec2_instance' }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh ('sudo docker build -t wakeup-portfolio .')
            }
        }
        stage('Run') {
            steps {
                echo 'Running....'
                sh ('sudo docker run wakeup-portfolio')
            }
        }
    }
}