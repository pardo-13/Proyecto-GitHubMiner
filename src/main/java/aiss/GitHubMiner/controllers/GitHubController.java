package aiss.GitHubMiner.controllers;

import aiss.GitHubMiner.model.GitMinerModel.ProjectGitMiner;
import aiss.GitHubMiner.repository.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private Transformation transformation;
    private RestTemplate restTemplate;

    @Autowired
    public GitHubController(Transformation transformation) { this.transformation = transformation;
    this.restTemplate = new RestTemplate();}

    private final String gitMinerUri ="http://localhost:8080/gitminer/projects";

    //GET http://localhost:8082/github/owner/repo
    @GetMapping("/{owner}/{repo}")
    public ProjectGitMiner getProject(@PathVariable String owner, @PathVariable String repo,
                                      @RequestParam(defaultValue = "5") Integer sinceCommits,
                                      @RequestParam(defaultValue = "20") Integer sinceIssues,
                                      @RequestParam(defaultValue = "2") Integer maxPages) {
        return transformation.getProject(owner, repo, sinceCommits, sinceIssues, maxPages);
    }

    //POST http://localhost:8082/github/owner/repo
    @PostMapping("/{owner}/{repo}")
    public ProjectGitMiner sendProject(@PathVariable String owner, @PathVariable String repo,
                                      @RequestParam(defaultValue = "5") Integer sinceCommits,
                                      @RequestParam(defaultValue = "20") Integer sinceIssues,
                                      @RequestParam(defaultValue = "2") Integer maxPages) {
        ProjectGitMiner project= transformation.getProject(owner, repo, sinceCommits, sinceIssues, maxPages);
        HttpEntity<ProjectGitMiner> request = new HttpEntity<>(project);
        ResponseEntity<ProjectGitMiner> response = restTemplate.exchange(gitMinerUri, HttpMethod.POST, request, ProjectGitMiner.class);
        return response.getBody();
    }

}
