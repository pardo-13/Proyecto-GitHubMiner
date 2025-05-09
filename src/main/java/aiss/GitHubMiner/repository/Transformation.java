package aiss.GitHubMiner.repository;



import aiss.GitHubMiner.model.DataModel.Comment;
import aiss.GitHubMiner.model.DataModel.Project;
import aiss.GitHubMiner.model.DataModel.commit.Commits;
import aiss.GitHubMiner.model.DataModel.issue.Issue;
import aiss.GitHubMiner.model.DataModel.issue.Label;
import aiss.GitHubMiner.model.DataModel.User;
import aiss.GitHubMiner.model.DataModel.issue.Reactions;
import aiss.GitHubMiner.model.GitMinerModel.*;
import aiss.GitHubMiner.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
    public class Transformation {
        @Autowired
        GitHubService gitHubService;

        public ProjectGitMiner transform(Project project, Issue[] issues, Commits[] commits, String owner, String repoName) {
            //CREAMOS LAS LISTAS PARA CONVERTIR LOS ARRAYS EN LISTAS
            List<CommitGitMiner> commitsList = new ArrayList<>();
            List<IssueGitMiner> issueList = new ArrayList<>();


            for(Issue issue : issues) { //RECORREMOS CADA ISSUE
                String id = issue.getNumber(); //BUSCAMOS LOS COMENTARIOS DE CADA ISSUE
                Comment[] comments = gitHubService.getComment(owner, repoName, id);
                List<CommentGitMiner> commentList = new ArrayList<>();
                //RECORREMOS LOS COMENTARIOS
                for(Comment comment : comments){
                    CommentGitMiner commentary = transformComment(comment);
                    commentList.add(commentary);
                }

                //TRANSFORMAMOS LOS ISSUES
                IssueGitMiner issueGitMiner = transformIssue(issue);
                issueGitMiner.setComments(commentList);
                issueList.add(issueGitMiner);
            }

            for(Commits commit: commits){
                CommitGitMiner commitGitMiner = transformCommit(commit);
                commitsList.add(commitGitMiner);
            }


            ProjectGitMiner projectGitMiner = new ProjectGitMiner();
            projectGitMiner.setName(project.getName());
            projectGitMiner.setWebUrl(project.getWebUrl());
            projectGitMiner.setCommits(commitsList);
            projectGitMiner.setIssues(issueList);
            projectGitMiner.setId(project.getId());

            return projectGitMiner;
        }

        public UserGitMiner transformUser(User user) {
            UserGitMiner userGitMiner = new UserGitMiner();
            //TRANSFORMARMOS EL USUARIO A LA CLASE DE GITMINER
            userGitMiner.setUsername(user.getUsername());
            userGitMiner.setAvatarUrl(user.getAvatarUrl());
            userGitMiner.setWebUrl(user.getWebUrl());
            userGitMiner.setId(user.getId());
            return userGitMiner;
        }

        public CommentGitMiner transformComment(Comment comment) {
            UserGitMiner autor = comment.getAuthor()!=null?transformUser(comment.getAuthor()):null;
            CommentGitMiner commentGitMiner = new CommentGitMiner();
            //TRANSFORMAMOS EL COMENTARIO
            commentGitMiner.setAuthor(autor);
            commentGitMiner.setBody(comment.getBody());
            commentGitMiner.setCreatedAt(comment.getCreatedAt());
            commentGitMiner.setUpdatedAt(comment.getUpdatedAt());
            commentGitMiner.setId(comment.getId());
            return commentGitMiner;
        }

        public IssueGitMiner transformIssue(Issue issue) {

            List<String> newLabels = issue.getLabels().stream()
                    .map(Label::getDescription)
                    .collect(Collectors.toList());

            UserGitMiner asigneee = issue.getAssignee()!=null? transformUser(issue.getAssignee()): null;
            UserGitMiner autor = issue.getAuthor()!=null?transformUser(issue.getAuthor()):null;
            IssueGitMiner issueGitMiner = new IssueGitMiner();
            issueGitMiner.setClosedAt(issue.getClosedAt());
            issueGitMiner.setCreatedAt(issue.getCreatedAt());
            issueGitMiner.setUpdatedAt(issue.getUpdatedAt());
            issueGitMiner.setTitle(issue.getTitle());
            issueGitMiner.setAssignee(asigneee);
            issueGitMiner.setDescription(issue.getDescription());
            issueGitMiner.setLabels(newLabels);
            issueGitMiner.setState(issue.getState());
            issueGitMiner.setVotes(issue.getReactions().getTotalCount());
            issueGitMiner.setAuthor(autor);
            issueGitMiner.setId(issue.getId());

            return issueGitMiner;
        }

        public CommitGitMiner transformCommit(Commits commit) {
            CommitGitMiner commitGitMiner = new CommitGitMiner();
            commitGitMiner.setAuthoredDate(commit.getCommit().getAuthor().getDate().toString());
            commitGitMiner.setAuthorEmail(commit.getCommit().getAuthor().getEmail());
            commitGitMiner.setAuthorName(commit.getCommit().getAuthor().getEmail());
            commitGitMiner.setMessage(commit.getCommit().getMessage());
            commitGitMiner.setTitle(commit.getCommit().getMessage());
            commitGitMiner.setWebUrl(commit.getCommit().getUrl());
            commitGitMiner.setId(commit.getSha());
            return commitGitMiner;
        }
    }

