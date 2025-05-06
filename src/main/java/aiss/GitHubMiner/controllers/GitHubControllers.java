package aiss.GitHubMiner.controllers;

import aiss.GitHubMiner.model.Comment;
import aiss.GitHubMiner.model.Commit;
import aiss.GitHubMiner.model.issue.Issue;
import aiss.GitHubMiner.model.Project;
import aiss.GitHubMiner.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/{owner}/{repoName}")
public class GitHubControllers {

    @Autowired
    GitHubService gitHubService;
    @PostMapping
    public Project getData(@PathVariable String owner, @PathVariable String repoName,
                         @RequestParam(required = false, defaultValue = "20") Integer sinceIssues,
                         @RequestParam(required = false, defaultValue = "2") Integer maxPages,
                           @RequestParam(required = false, defaultValue = "5") Integer sinceCommits) {

        Project project = gitHubService.getProject(owner, repoName); //BUSCAMOS EL PROYECTO

        Issue[] issues = gitHubService.getIssues(owner,repoName,sinceIssues,maxPages); //BUCAMOS LOS ISSUES
        Commit[] commits = gitHubService.getCommits(owner,repoName, sinceCommits, maxPages); //BUSCAMOS LOS COMMITS

        //CREAMOS LAS LISTAS PARA CONVERTIR LOS ARRAYS EN LISTAS
        List<Commit> commitsList = new ArrayList<>();
        List<Issue> issueList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();

        for(Issue issue : issues) { //RECORREMOS CADA ISSUE
            String id = issue.getNumber(); //BUSCAMOS LOS COMENTARIOS DE CADA ISSUE
            Comment[] comments = gitHubService.getComment(owner, repoName, id);
            for(Comment comment : comments){
                commentList.add(comment); //AÑADIMOS LOS COMENTARIOS A LA LISTA
            }
            issue.setComments(commentList);
            issueList.add(issue); //AÑADIMOS EL ISSUE A LA LISTA
        }

        for(Commit commit: commits){
            commitsList.add(commit); //AÑADIMOS LOS COMMITS A LA LISTA
        }

        project.setCommits(commitsList);
        project.setIssues(issueList);
        List<Issue> projectIssues = project.getIssues();


        return project;

    }

}
