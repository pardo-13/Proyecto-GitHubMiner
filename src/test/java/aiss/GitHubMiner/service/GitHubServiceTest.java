package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.Comment;
import aiss.GitHubMiner.model.Commit;
import aiss.GitHubMiner.model.issue.Issue;
import aiss.GitHubMiner.model.Project;
import org.hibernate.annotations.Comments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubServiceTest {
    String owner = "spring-projects";
    String repo = "spring-framework";
    @Autowired
    GitHubService gitHubService;
    @Test

    public void testGetProject(){
        Project project = gitHubService.getProject(owner, repo);
        assertNotNull(project);
        System.out.println(project);
    }

    @Test
    void getIssues() {
        Issue[] issues = gitHubService.getIssues(owner, repo,10,1);
        assertNotNull(issues);
        System.out.println("-------------------------------------------------");
        for(Issue issue : issues){
            System.out.println(
                    "Id:" + issue.getId() + "Title:" + issue.getTitle() + "Description: " + issue.getDescription()
            );
        }
        System.out.println(issues.length);
    }

    @Test
    void getCommits() {
        Commit[] commits = gitHubService.getCommits(owner, repo,1,1);
        assertNotNull(commits);
    }

    @Test
    void getComment() {
        Comment[] comments = gitHubService.getComment(owner,repo,"1");
        System.out.println(comments.length);
        assertNotNull(comments);
    }
}