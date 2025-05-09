package aiss.GitHubMiner.model.GitMinerModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
*/

@Entity
@Table(name = "Project")
public class ProjectGitMiner {

    @Id
    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    @NotEmpty(message = "The name of the project cannot be empty")
    public String name;

    @JsonProperty("web_url")
    @NotEmpty(message = "The URL of the project cannot be empty")
    public String webUrl;
    @JsonProperty("commits")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    private List<CommitGitMiner> commitGitMiners;

    @JsonProperty("issues")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    private List<IssueGitMiner> issueGitMiners;

    public ProjectGitMiner() {
        commitGitMiners = new ArrayList<>();
        issueGitMiners = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<CommitGitMiner> getCommits() {
        return commitGitMiners;
    }

    public void setCommits(List<CommitGitMiner> commitGitMiners) {
        this.commitGitMiners = commitGitMiners;
    }

    public List<IssueGitMiner> getIssues() {
        return issueGitMiners;
    }

    public void setIssues(List<IssueGitMiner> issueGitMiners) {
        this.issueGitMiners = issueGitMiners;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProjectGitMiner.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commitGitMiners == null)?"<null>":this.commitGitMiners));
        sb.append(',');
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issueGitMiners == null)?"<null>":this.issueGitMiners));
        sb.append(',');

        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}