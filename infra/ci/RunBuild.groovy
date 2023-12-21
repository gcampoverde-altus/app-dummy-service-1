@Library('pipeline-library-jenkins') _
pipeline{

    agent {
        node {
            label 'terraform1.2.5-ci'
        }
    }

    environment {
        app_dir="${WORKSPACE}/app-dummy-service-1"
    }

    parameters {
          string defaultValue: 'develop', description: 'Select the desired branch and please make sure that the branch name matches with git branch', name: 'branch'

    }


    stages {

        stage('Check out source code')
        {
            steps{
                    script
                    {

                        withCredentials([usernamePassword(credentialsId: 'GitHubAppIntegration',
                                          usernameVariable: 'GITHUB_APP',
                                          passwordVariable: 'GITHUB_ACCESS_TOKEN')]) {

                             sh """
                                    git clone -b $branch https://"\$GITHUB_ACCESS_TOKEN":x-oauth-basic@github.com/gcampoverde-altus/app-dummy-service-1.git
                                    cd $env.app_dir
                                    ls
                                """
                        }
                    }
                }
        }
        stage ('Clone Fancy Way')
        {
            steps{
                git branch: 'develop', changelog: false, credentialsId: 'GitHubAppIntegration', poll: false, url: 'https://github.com/gcampoverde-altus/app-dummy-service-1'
                sh """
                                    cd $env.app_dir
                                    ls
                                    echo 'Clonned'
                                """
            }
        }
        
    }
}
