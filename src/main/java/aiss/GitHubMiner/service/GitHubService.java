package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.issue.Issue;
import aiss.GitHubMiner.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GitHubService {

     @Value("La uri de mi repositorio")
    private String baseUri;

    @Value("Mi token")
    private String token;
    
    @Autowired
    RestTemplate restTemplate;
    public Project getProject(String owner, String repo){
        String url = "https://api.github.com/repos/" + owner + "/" + repo;
        Project project = restTemplate.getForObject(url, Project.class);
        return project;
    }
    public Issue[] getIssues(String owner, String repo, Integer days, Integer page){
        LocalDateTime today = LocalDateTime.now();
        today = today.minusDays(days);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String day = today.format(formato);
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/issues" + "?since=" + day + "&page=" + page;
        Issue[] issues = restTemplate.getForObject(url, Issue[].class);
        return issues;
    }

    public Commit[] getCommits(String owner, String repo) {
        String url = baseUri + owner + "/" + repo;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Commit[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commit[].class);
        return response.getBody();
    }

    public Comment[] getComment(String owner, String repo) {
        String url = baseUri + owner + "/" + repo;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Comment[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Comment[].class);
        return response.getBody();
    }

    public User getUser(String owner, String repo) {
        String url = baseUri + owner + "/" + repo;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity <User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
        return response.getBody();
    }
}
