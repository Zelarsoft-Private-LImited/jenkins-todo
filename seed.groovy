folder('CI-Pipeline') {
    displayName('CI Pipeline')
    description('CI Pipeline')
}
def component = ["frontend", "login", "users" , "todo"];

def count=(component.size()-1)
for (i in 0..count) {
    def j=component[i]
    pipelineJob("CI-Pipeline/${j}") {
        configure { flowdefinition ->
            flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
            }
            flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
                'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
                    'userRemoteConfigs' {
                        'hudson.plugins.git.UserRemoteConfig' {
                            'url'('https://github.com/srikavyapendiala/'+j+'.git')
                        }
                    }
                    'branches' {
                        'hudson.plugins.git.BranchSpec' {
                            'name'('/main/')
                        }
                    }
                }
                'scriptPath'('Jenkinsfile')
                'lightweight'(true)
            }
        }
    }
}
