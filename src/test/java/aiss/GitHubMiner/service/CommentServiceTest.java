package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.CommentData.CommentDatum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {


    @Autowired
    private CommentService commentService;

    @Test
    void getComments() {
        List<CommentDatum> comments = commentService.getComments("https://api.github.com/repos/spring-projects/spring-framework/issues/34812/comments");
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        System.out.println(comments);
        System.out.println(comments.size());
    }

}