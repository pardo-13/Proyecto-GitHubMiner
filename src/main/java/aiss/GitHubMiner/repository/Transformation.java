package aiss.GitHubMiner.repository;

import aiss.GitHubMiner.model.Comment;
import aiss.GitHubMiner.model.Commit;
import aiss.GitHubMiner.model.Project;
import aiss.GitHubMiner.model.issue.Issue;
import aiss.GitHubMiner.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Transformation {
    @Autowired
    GitHubService gitHubService;

    public Project transform(Project project, Issue[] issues, Commit[] commits, String owner, String repoName) {
        //CREAMOS LAS LISTAS PARA CONVERTIR LOS ARRAYS EN LISTAS
        List<Commit> commitsList = new ArrayList<>();
        List<Issue> issueList = new ArrayList<>();


        for(Issue issue : issues) { //RECORREMOS CADA ISSUE
            String id = issue.getNumber(); //BUSCAMOS LOS COMENTARIOS DE CADA ISSUE
            Comment[] comments = gitHubService.getComment(owner, repoName, id);
            List<Comment> commentList = new ArrayList<>();
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

        return project;
    }
}
