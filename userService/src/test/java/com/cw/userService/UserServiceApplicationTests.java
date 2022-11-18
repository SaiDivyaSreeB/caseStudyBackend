package com.cw.userService;

import com.cw.userService.models.Roles;
import com.cw.userService.models.Users;
import com.cw.userService.models.rating;
import com.cw.userService.models.washPack;
import com.cw.userService.repositories.ratingRepository;
import com.cw.userService.services.ratingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	ratingService rs;
	@MockBean
	ratingRepository rr;
	@Test

	public void addRatingTest(){
		rating ratings = new rating("a","w1","good",5,"");
		when(rr.save(ratings)).thenReturn(ratings);
		assertEquals(ratings,rs.addRating(ratings));
	}

	@Test
	public void getAllRatingsTest(){
		when(rr.findAll()).thenReturn(Stream.of(

						new rating("a","w1","good",5,""),
						new rating("b","w1","good",5,""),
						new rating("c","w1","good",5,"")
				)
				.collect(Collectors.toList()));
		assertEquals(3,rs.getAllRatings().size());
	}
	@Test
	public void washerSpecificRatingsTest(){
		rating ratings = new rating("a","w1","good",5,"");
		when(rr.findAll()).thenReturn(Stream.of(

						new rating("b","w2","good",5,""),
						new rating("c","w1","good",5,""),
						new rating("d","w1","good",5,"")
				)
				.collect(Collectors.toList()));

		assertEquals(2,rs.washerSpecific("w1").size());

	}
//	@Test
//	public void updateProfile(){
//		Roles role = new Roles("b","WASHER");
//		Set<Roles> roles = [role];
//		Users user = new Users("a","abcd@gmail.com","abcd","token1234","abcd",true,"",);
//	}

}
