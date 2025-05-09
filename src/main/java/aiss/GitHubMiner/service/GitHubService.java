package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.Comment;
import aiss.GitHubMiner.model.DataModel.commit.Commit;
import aiss.GitHubMiner.model.DataModel.commit.Commits;
import aiss.GitHubMiner.model.DataModel.issue.Issue;
import aiss.GitHubMiner.model.DataModel.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String token;
    
    @Autowired
    RestTemplate restTemplate;
    public Project getProject(String owner, String repo){
        String url = "https://api.github.com/repos/" + owner + "/" + repo;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Project> response = restTemplate.exchange(url, HttpMethod.GET, entity, Project.class);
        return response.getBody();
    }
    public Issue[] getIssues(String owner, String repo, Integer days, Integer page){
        LocalDateTime today = LocalDateTime.now();
        today = today.minusDays(days);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String day = today.format(formato);
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/issues?since=" + day + "&page=" + page ;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Issue[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Issue[].class);
        return response.getBody();
    }

    public Commits[] getCommits(String owner, String repo, Integer days, Integer page) {

        LocalDateTime today = LocalDateTime.now();
        today = today.minusDays(days);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String day = today.format(formato);

        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/commits?since=" + day + "&page=" + page ;

        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Commits[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commits[].class);
        return response.getBody();
    }

    public Comment[] getComment(String owner, String repo, String issueNumber) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" +issueNumber + "/comments";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Comment[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Comment[].class);
        return response.getBody();
    }


}
