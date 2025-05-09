package aiss.GitHubMiner.controllers;

import aiss.GitHubMiner.model.DataModel.commit.Commit;
import aiss.GitHubMiner.model.DataModel.commit.Commits;
import aiss.GitHubMiner.model.DataModel.issue.Issue;
import aiss.GitHubMiner.model.DataModel.Project;
import aiss.GitHubMiner.model.GitMinerModel.ProjectGitMiner;
import aiss.GitHubMiner.repository.Transformation;
import aiss.GitHubMiner.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/{owner}/{repoName}")
public class GitHubControllers {

    @Value("${gitMiner.url}")
    private String gitMinerUrl;
    @Autowired
    GitHubService gitHubService;
    @Autowired
    Transformation transformation;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ProjectGitMiner getProject(@PathVariable("owner") String owner, @PathVariable("repoName") String repoName,
                              @RequestParam(required = false, defaultValue = "20") Integer sinceIssues,
                              @RequestParam(required = false, defaultValue = "2") Integer maxPages,
                              @RequestParam(required = false, defaultValue = "5") Integer sinceCommits) {
        Project project = gitHubService.getProject(owner, repoName); //BUSCAMOS EL PROYECTO
        Issue[] issues = gitHubService.getIssues(owner,repoName,sinceIssues,maxPages); //BUCAMOS LOS ISSUES
        Commits[] commits = gitHubService.getCommits(owner,repoName, sinceCommits, maxPages); //BUSCAMOS LOS COMMITS

        ProjectGitMiner transformated_project = transformation.transform(project,issues,commits,owner,repoName);
        return transformated_project;
    }
    @PostMapping
    public ResponseEntity<ProjectGitMiner> getData(@PathVariable String owner, @PathVariable String repoName,
                                           @RequestParam(required = false, defaultValue = "20") Integer sinceIssues,
                                           @RequestParam(required = false, defaultValue = "2") Integer maxPages,
                                           @RequestParam(required = false, defaultValue = "5") Integer sinceCommits) {

        Project project = gitHubService.getProject(owner, repoName); //BUSCAMOS EL PROYECTO
        Issue[] issues = gitHubService.getIssues(owner,repoName,sinceIssues,maxPages); //BUCAMOS LOS ISSUES
        Commits[] commits = gitHubService.getCommits(owner,repoName, sinceCommits, maxPages); //BUSCAMOS LOS COMMITS

       ProjectGitMiner transformated_project = transformation.transform(project,issues,commits,owner,repoName);
        System.out.println(transformated_project);
        return restTemplate.postForEntity(gitMinerUrl, transformated_project, ProjectGitMiner.class);

    }

}
