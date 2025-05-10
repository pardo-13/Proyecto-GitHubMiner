package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.DataModel.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUser() {
        UserData user = userService.getUser("https://api.github.com/users/quaff");
        assertNotNull(user);
        System.out.println(user);
    }
}