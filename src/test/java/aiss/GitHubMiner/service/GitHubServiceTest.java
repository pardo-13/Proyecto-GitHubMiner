package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.Comment;
import aiss.GitHubMiner.model.DataModel.commit.Commit;
import aiss.GitHubMiner.model.DataModel.commit.Commits;
import aiss.GitHubMiner.model.DataModel.issue.Issue;
import aiss.GitHubMiner.model.DataModel.Project;
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
        Commits[] commits = gitHubService.getCommits(owner, repo,1,1);
        assertNotNull(commits);
        for(Commits commit : commits){
            System.out.println(commit.getCommit().getMessage());
        }
    }

    @Test
    void getComment() {
        Comment[] comments = gitHubService.getComment(owner,repo,"5");
        for(Comment comment : comments){
            System.out.println(comment.getAuthor().getUsername());
        }
        assertNotNull(comments);
    }
}