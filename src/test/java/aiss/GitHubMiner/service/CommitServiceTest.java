package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.CommitData.CommitDatum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceTest {

    String owner = "spring-projects";
    String repo = "spring-framework";
    Integer days = 30;
    Integer page = 1;

    @Autowired
    private CommitService commitService;

    @Test
    void getCommits() {
        List<CommitDatum> commits = commitService.getCommits(owner, repo, days, page);
        assertNotNull(commits);
        assertFalse(commits.isEmpty());
        System.out.println(commits);
        System.out.println(commits.size());
    }

}