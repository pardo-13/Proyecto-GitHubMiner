package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.IssueData.IssueDatum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IssueServiceTest {

    String owner = "spring-projects";
    String repo = "spring-framework";
    Integer days = 90;
    Integer page = 3;

    @Autowired
    private IssueService issueService;

    @Test
    void getIssues() {
        List<IssueDatum> issues = issueService.getIssues(owner, repo, days, page);
        assertNotNull(issues);
        assertFalse(issues.isEmpty());
        System.out.println(issues);
        System.out.println(issues.size());
    }

}