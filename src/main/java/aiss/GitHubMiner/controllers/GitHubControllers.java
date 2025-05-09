package aiss.GitHubMiner.controllers;

import aiss.GitHubMiner.model.Comment;
import aiss.GitHubMiner.model.Commit;
import aiss.GitHubMiner.model.issue.Issue;
import aiss.GitHubMiner.model.Project;
import aiss.GitHubMiner.repository.Transformation;
import aiss.GitHubMiner.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public Project getProject(@PathVariable("owner") String owner, @PathVariable("repoName") String repoName,
                              @RequestParam(required = false, defaultValue = "20") Integer sinceIssues,
                              @RequestParam(required = false, defaultValue = "2") Integer maxPages,
                              @RequestParam(required = false, defaultValue = "5") Integer sinceCommits) {
        Project project = gitHubService.getProject(owner, repoName); //BUSCAMOS EL PROYECTO
        Issue[] issues = gitHubService.getIssues(owner,repoName,sinceIssues,maxPages); //BUCAMOS LOS ISSUES
        Commit[] commits = gitHubService.getCommits(owner,repoName, sinceCommits, maxPages); //BUSCAMOS LOS COMMITS

        Project transformated_project = transformation.transform(project,issues,commits,owner,repoName);
        return transformated_project;
    }
    @PostMapping
    public ResponseEntity<Project> getData(@PathVariable String owner, @PathVariable String repoName,
                                           @RequestParam(required = false, defaultValue = "20") Integer sinceIssues,
                                           @RequestParam(required = false, defaultValue = "2") Integer maxPages,
                                           @RequestParam(required = false, defaultValue = "5") Integer sinceCommits) {

        Project project = gitHubService.getProject(owner, repoName); //BUSCAMOS EL PROYECTO
        Issue[] issues = gitHubService.getIssues(owner,repoName,sinceIssues,maxPages); //BUCAMOS LOS ISSUES
        Commit[] commits = gitHubService.getCommits(owner,repoName, sinceCommits, maxPages); //BUSCAMOS LOS COMMITS

       Project transformated_project = transformation.transform(project,issues,commits,owner,repoName);

        return restTemplate.postForEntity(gitMinerUrl, transformated_project, Project.class);

    }

}
