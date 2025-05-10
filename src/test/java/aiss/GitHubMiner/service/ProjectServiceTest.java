package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.ProjectData;
import aiss.GitHubMiner.model.GitMinerModel.ProjectGitMiner;
import aiss.GitHubMiner.repository.Transformation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    String owner = "spring-projects";
    String repo = "spring-framework";

    @Autowired
    private ProjectService projectService;
    @Autowired
    private Transformation transformation;

    @Test
    void getProject() {
        ProjectData project = projectService.getProject(owner, repo);
        assertNotNull(project);
        System.out.println(project);
    }

    @Test
    void getProjectMiner() {
        ProjectGitMiner project = transformation.getProject(owner, repo,5,30,2);
        System.out.println(project);
        System.out.println(project.getCommits().size());
        System.out.println(project.getCommits().get(0));
        System.out.println(project.getIssues().size());
        System.out.println(project.getIssues().get(0).getComments().size());
        System.out.println(project.getIssues().get(0).getAuthor());
    }
  
}