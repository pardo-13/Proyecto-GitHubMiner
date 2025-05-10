package aiss.GitHubMiner.service;


import aiss.GitHubMiner.model.DataModel.CommitData.CommitDatum;
import aiss.GitHubMiner.model.DataModel.IssueData.IssueDatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CommitService {

    RestTemplate restTemplate;

    @Autowired
    public CommitService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    @Value("${github.baseuri}")
    private String baseuri;

    @Value("${github.token}")
    private String token;

    public List<CommitDatum> getCommits(String owner, String repo, Integer days, Integer maxPages) {
        List<CommitDatum> commits = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        today = today.minusDays(days);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String day = today.format(formato);
        int page = 1;
        String uri = baseuri+"repos/"+owner+ "/"+repo + "/commits?since="+day+"&page="+page;
        HttpHeaders headers = new HttpHeaders();
        if(!Objects.equals(token, "")) {
            headers.set("Authorization", "Bearer "+token);
        }
        HttpEntity<CommitDatum[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<CommitDatum[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, CommitDatum[].class);
        List<CommitDatum> commits1 = Arrays.asList(response.getBody());
        commits.addAll(commits1);
        while (page < maxPages) {
            page++;
            uri = baseuri + "repos/" + owner + "/" + repo + "/issues?since=" + day + "&page=" + page;
            response = restTemplate.exchange(uri, HttpMethod.GET, request, CommitDatum[].class);
            List<CommitDatum> commits2 = Arrays.asList(response.getBody());
            commits.addAll(commits2);
        }
        return commits;
    }
}