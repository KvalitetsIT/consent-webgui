podTemplate(
        containers: [containerTemplate(image: 'docker', name: 'docker', command: 'cat', ttyEnabled: true)],
        volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
        defaultContainer: "docker",
) {
    node(POD_LABEL) {
        stage('Clone repository') {
            checkout scm
        }
        stage('Build And Test') {
            container('docker') {
                def maven = docker.image('maven:3-openjdk-14')
                maven.pull()
                maven.inside("-v /var/run/docker.sock:/var/run/docker.sock") {
                    sh 'mvn install'
                }
             }
        }
        stage('Tag Docker Images And Push') {
            container('docker') {
                def commitID = commits = sh(
                        script: "git rev-parse HEAD",
                        returnStdout: true
                )
                def images = ["kvalitetsit/consent-service", "kvalitetsit/consent-idp", "kvalitetsit/consent-webgui", "kvalitetsit/consent-admingui"]
                docker.withRegistry('', 'dockerhub') {
                    for (image in images) {
                        img = docker.image(image + ":${commitID}")
                        img.push("${env.GIT_COMMIT}")
                        img.push("dev")
                        if (env.TAG_NAME != null && env.TAG_NAME.matches("^v[0-9]*\\.[0-9]*\\.[0-9]*")) {
                            echo "Tagging version"
                            img.push(env.TAG_NAME.substring(1))
                            img.push("latest")
                        }
                    }
                }
            }
        }
    }
 }