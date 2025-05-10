package aiss.GitHubMiner.repository;

import aiss.GitHubMiner.model.DataModel.CommentData.CommentDatum;
import aiss.GitHubMiner.model.DataModel.CommitData.CommitDatum;
import aiss.GitHubMiner.model.DataModel.IssueData.IssueDatum;
import aiss.GitHubMiner.model.DataModel.ProjectData;
import aiss.GitHubMiner.model.DataModel.UserData;
import aiss.GitHubMiner.model.GitMinerModel.*;
import aiss.GitHubMiner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class Transformation {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommitService commitService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    //GET
    public ProjectGitMiner getProject(String owner, String repo,
                                      Integer sinceCommits, Integer sinceIssues, Integer maxPages ) {
        ProjectGitMiner project = new ProjectGitMiner();
        ProjectData data = projectService.getProject(owner, repo);
        project.setId(data.getId().toString());
        project.setName(data.getName());
        project.setWebUrl(data.getHtmlUrl());
        project.setCommits(getCommits(owner, repo, sinceCommits, maxPages));
        project.setIssues(getIssues(owner, repo, sinceIssues, maxPages));
        return project;
    }

    private List<CommitGitMiner> getCommits(String owner, String repo, Integer sinceCommits, Integer maxPages) {
        List<CommitGitMiner> commits = new ArrayList<>();
        List<CommitDatum> datas = commitService.getCommits(owner, repo, sinceCommits, maxPages);
        for (CommitDatum data : datas) {
            CommitGitMiner commit = new CommitGitMiner();
            commit.setId(data.getSha());
            commit.setTitle("");
            commit.setMessage(data.getCommit().getMessage());
            commit.setAuthorName(data.getCommit().getAuthor().getName());
            commit.setAuthorEmail(data.getCommit().getAuthor().getEmail());
            commit.setAuthoredDate(data.getCommit().getAuthor().getDate());
            commit.setWebUrl(data.getHtmlUrl());
            commits.add(commit);
        }
        return commits;
    }

    private List<IssueGitMiner> getIssues(String owner, String repo, Integer sinceIssues, Integer maxPages) {
        List<IssueGitMiner> issues = new ArrayList<>();
        List<IssueDatum> datas = issueService.getIssues(owner, repo, sinceIssues, maxPages);
        for (IssueDatum data : datas) {
            IssueGitMiner issue = new IssueGitMiner();
            issue.setId(data.getId().toString());
            issue.setTitle(data.getTitle());
            issue.setDescription(data.getBody());
            issue.setState(data.getState());
            issue.setCreatedAt(data.getCreatedAt());
            issue.setUpdatedAt(data.getUpdatedAt());
            issue.setClosedAt(data.getClosedAt());
            issue.setLabels(data.getLabels().stream().map(l -> l.getName()).collect(Collectors.toList()));
            issue.setVotes(data.getReactions().getTotalCount());
            String user_url = data.getUser().getUrl();
            if(user_url.contains("%5B")){
                user_url = user_url.replace("%5B","[").replace("%5D","]");
            }
            issue.setAuthor(getUser(user_url));
            if(data.getAssignee() != null) {
                issue.setAssignee(getUser(data.getAssignee().getUrl()));
            }
            issue.setComments(getComments(data.getCommentsUrl()));
            issues.add(issue);
        }
        return issues;
    }

    private UserGitMiner getUser(String url) {
        UserGitMiner user = new UserGitMiner();
        UserData data = userService.getUser(url);
        user.setId(data.getId().toString());
        user.setName(data.getName());
        user.setUsername(data.getLogin());
        user.setAvatarUrl(data.getAvatarUrl());
        user.setWebUrl(data.getHtmlUrl());
        return user;
    }

    private List<CommentGitMiner> getComments(String url) {
        List<CommentGitMiner> comments = new ArrayList<>();
        List<CommentDatum> datas = commentService.getComments(url);
        for (CommentDatum data : datas) {
            CommentGitMiner comment = new CommentGitMiner();
            comment.setId(data.getId().toString());
            comment.setBody(data.getBody());
            comment.setCreatedAt(data.getCreatedAt());
            comment.setUpdatedAt(data.getUpdatedAt());
            comment.setAuthor(getUser(data.getUser().getUrl()));
            comments.add(comment);
        }
        return comments;
    }

}
