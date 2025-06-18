package com.example.demo;

import com.example.demo.domain.BoardVO;
import com.example.demo.repository.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private BoardMapper bmap;

	@Test
	void contextLoads() {
		for (int i = 0; i <= 300; i++) {
			BoardVO bvo
					= BoardVO.builder()
					.title("test title" + ((int) (Math.random() * 100) + 1))
					.writer("test" + ((int) (Math.random() * 100) + 1) + "@gmail.com")
					.content("test content" + ((int) (Math.random() * 100) + 1))
					.build();

			bmap.insert(bvo);
		}
	}
}
