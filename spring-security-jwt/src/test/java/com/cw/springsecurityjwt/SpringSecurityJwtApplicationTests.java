package com.cw.springsecurityjwt;

import com.cw.springsecurityjwt.exceptionhandler.APIRequestException;
import com.cw.springsecurityjwt.models.washPack;
import com.cw.springsecurityjwt.repositories.washPackRepository;
import com.cw.springsecurityjwt.services.washPackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringSecurityJwtApplicationTests {

	@Autowired
	washPackService ws;
	@MockBean
	washPackRepository wr;

	@Test
	public void findByIdTest() {
		washPack pack = new washPack("a", "pack1", 1, "xyz", "");
		when(wr.findById("a")).thenReturn(Optional.of(pack));
		assertEquals(pack, ws.findById("a").getBody());
	}

	@Test
	public void findByIdExceptionTest() {
		washPack pack = new washPack("a", "pack1", 1, "xyz", "");
		when(wr.findById("a")).thenReturn(Optional.of(pack));
		assertThrows(APIRequestException.class, () -> ws.findById("b"));
	}

	@Test
	public void findByNameTest() {
		washPack pack = new washPack("a", "pack1", 1, "xyz", "");
		when(wr.findAll()).thenReturn(Stream.of(new washPack("a", "pack1", 1, "xyz", ""),
				new washPack("b", "pack2", 2, "abc", "")).collect(Collectors.toList()));
		assertEquals("pack1", ws.findByName("pack1").getName());
		assertEquals(1, ws.findByName("pack1").getCost());
		assertEquals("a", ws.findByName("pack1").getId());
		assertEquals("xyz", ws.findByName("pack1").getDescription());
		assertEquals("", ws.findByName("pack1").getImage());
	}

	@Test
	public void findByNameExceptionTest() {
		washPack pack = new washPack("a", "pack1", 1, "xyz", "");
		when(wr.findAll()).thenReturn(Stream.of(new washPack("a", "pack1", 1, "xyz", ""),
				new washPack("b", "pack2", 1, "abc", "")).collect(Collectors.toList()));
		assertThrows(NoSuchElementException.class, () -> ws.findByName("pack3"));
	}

	@Test
	public void updateWashpackTest() {
		washPack pack = new washPack("a", "pack1", 1, "xyz", "");
		washPack packtoUpdate = new washPack("a", "pack1", 2, "xyz", "JHB");
		when(wr.findById("a")).thenReturn(Optional.of(pack));
		when(wr.save(pack)).thenReturn(pack);
		assertEquals(packtoUpdate.getId(), ws.updateWp("a", packtoUpdate).getBody().getId());
		assertEquals(packtoUpdate.getName(), ws.updateWp("a", packtoUpdate).getBody().getName());
		assertEquals(packtoUpdate.getCost(), ws.updateWp("a", packtoUpdate).getBody().getCost());
		assertEquals(packtoUpdate.getDescription(), ws.updateWp("a", packtoUpdate).getBody().getDescription());
		assertEquals(packtoUpdate.getImage(), ws.updateWp("a", packtoUpdate).getBody().getImage());
	}
	@Test
	public void updateWashpackExceptionTest() {
		washPack pack = new washPack("b", "pack1", 1, "xyz", "");
		when(wr.findById("a")).thenReturn(Optional.of(pack));
		assertThrows(APIRequestException.class, () -> ws.updateWp("b",pack));
	}
	@Test
	public void deleteWashpack(){
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		washPack pack = new washPack("b", "pack1", 1, "xyz", "");
		when(wr.findById("b")).thenReturn(Optional.of(pack));
		assertEquals(response,ws.deleteWp("b").getBody());
	}
	@Test
	public void deleteOrderIdNotExceptionTest(){
		when(wr.findById("b")).thenReturn(Optional.of(new washPack("b", "pack1", 1, "xyz", "")));
		assertThrows(APIRequestException.class,()->ws.deleteWp("a").getBody());
	}
}

