package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.ProjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class ProjectService {

    RestTemplate restTemplate;

    @Autowired
    public ProjectService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    @Value("${github.baseuri}")
    private String baseuri;

    @Value("${github.token}")
    private String token;

    public ProjectData getProject(String workspace, String repo) {
        String uri = baseuri + "repos/" + workspace + "/" + repo;
        HttpHeaders headers = new HttpHeaders();
        if(!Objects.equals(token, "")) {
            headers.set("Authorization", "Bearer "+token);
        }
        HttpEntity<ProjectData> request = new HttpEntity<>(null, headers);
        ResponseEntity<ProjectData> response = restTemplate.exchange(uri, HttpMethod.GET, request, ProjectData.class);
        return response.getBody();
    }

}
