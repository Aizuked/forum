package com.forum.forum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ForumApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void userAddTest() throws Exception {
		String bookInJson = "{\"text\":\"test\",\"categories\":\"test\"}";
		ResultActions s = mockMvc.perform(post("/addNewPost")
						.contentType(MediaType.APPLICATION_JSON)
						.content("")
				)
				.andDo(print())
				.andExpect(status().isAccepted());
	}
}
