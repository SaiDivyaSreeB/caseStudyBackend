package com.cw.order;

import com.cw.order.controller.orderController;
import com.cw.order.exceptionHandlers.APIRequestException;
import com.cw.order.models.Car;
import com.cw.order.models.orderDetails;
import com.cw.order.repository.orderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderApplicationTests {

	@Autowired
	orderController oc;
	@MockBean
	orderRepository or;
	@Test
	public void allOrdersTest(){
		when(or.findAll()).thenReturn(Stream.of(
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning")
	).collect(Collectors.toList()));
		assertEquals(3,oc.findallOrders().size());
	}
	/*@Test
	public void findOrderByIdNotFoundExceptionTest(){
		when(or.findById("a")).thenReturn(Optional.of(new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning")));
		//orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning");
		assertThrows(APIRequestException.class,()->oc.findById("anyrandomId"));
	}*/
	@Test
	public void findOrderByIdTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		assertEquals(order,oc.findById("a").getBody());
	}
	/*@Test
	public void deleteOrderIdNotExceptionTest(){
		when(or.findById("a")).thenReturn(Optional.of(new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning")));
		assertThrows(APIRequestException.class,()->oc.deleteOrderById("anyrandomId"));
	}*/
	@Test
	public void deleteOrderByIdTest(){
		when(or.findById("a")).thenReturn(Optional.of(new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning")));
		//Map<String, Boolean> response = new HashMap<String,Boolean>();
		//Map<String,Boolean> expected = response.put("order deleted",Boolean.TRUE);
		Map<String, Boolean> response = new HashMap<>();
		response.put("order deleted",Boolean.TRUE);
		assertEquals(response,oc.deleteOrderById("a").getBody());
	}
	@Test
	public void addOrderTest(){
		//orderDetails expectedOrder = new orderDetails("a","z@gmail.com","NA","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.save(order)).thenReturn(order);

		assertEquals("Pending",order.getStatus());
		//assertEquals("gwen",oc.addOrder(order).getWasherName());
	}
	@Test
	public void findUnassignedTest(){
		when(or.findAll()).thenReturn(Stream.of(
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","NA","pack_1",1111115555,"888888","Cancelled",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","NA","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"))
				.collect(Collectors.toList()));
		assertEquals(1,oc.getUnassignedOrders().size());
	}
	/*@Test
	public void updateStatusIdNotFoundTest(){
		when(or.findById("a")).thenReturn(Optional.of(new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning")));
		//orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning");
		assertThrows(APIRequestException.class,()->oc.updateStatus("anyrandomId"));
	}*/
	@Test
	public void updateStatusTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		//orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.save(order)).thenReturn(order);
		assertEquals("Completed",oc.updateStatus("a").getBody().getStatus());
	}
	/*@Test
	public void updateOrderIdNotFoundExceptionTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		orderDetails order2 = new orderDetails("b","z@gmail.com","gwen","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		//orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning");
		assertThrows(APIRequestException.class,()->oc.updateOrder("b",order2));
	}*/
	@Test
	public void updateOrderTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","NA","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		orderDetails orderToUpdate = new orderDetails("a","zz@gmail.com","NA","pack_2",1111115555,"888888","NA",new Car(1,"Honda","City"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		when(or.save(order)).thenReturn(order);
		assertEquals(orderToUpdate.getOrderId(),oc.updateOrder("a",orderToUpdate).getBody().getOrderId());
		assertEquals(orderToUpdate.getUserEmailId(),oc.updateOrder("a",orderToUpdate).getBody().getUserEmailId());
		assertEquals(orderToUpdate.getWasherName(),oc.updateOrder("a",orderToUpdate).getBody().getWasherName());
		assertEquals(orderToUpdate.getWashpack(),oc.updateOrder("a",orderToUpdate).getBody().getWashpack());
		assertEquals(orderToUpdate.getPhoneNo(),oc.updateOrder("a",orderToUpdate).getBody().getPhoneNo());
		assertEquals(orderToUpdate.getAreapincode(),oc.updateOrder("a",orderToUpdate).getBody().getAreapincode());
		assertEquals(orderToUpdate.getStatus(),oc.updateOrder("a",orderToUpdate).getBody().getStatus());
		assertEquals(orderToUpdate.getCars(),oc.updateOrder("a",orderToUpdate).getBody().getCars());
		assertEquals(orderToUpdate.getAddon(),oc.updateOrder("a",orderToUpdate).getBody().getAddon());}

	@Test
	public void IdNotFoundExceptionTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		orderDetails order2 = new orderDetails("b","z@gmail.com","gwen","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		//orderDetails order = new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","pending",new Car(1,"BMW","X5"),"careful cleaning");
		assertAll(
				()->assertThrows(APIRequestException.class,()->oc.updateOrder("b",order2)),
				()->assertThrows(APIRequestException.class,()->oc.deleteOrderById("anyrandomId")),
				()->assertThrows(APIRequestException.class,()->oc.updateStatus("anyrandomId")),
				()->assertThrows(APIRequestException.class,()->oc.findById("anyrandomId")),
				()->assertThrows(APIRequestException.class,()->oc.cancelOrder(order2)),
				()->assertThrows(APIRequestException.class,()->oc.assignWasher("b","gwen"))
		);
	}
	@Test
	public void cancelOrderTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","NA","pack_1",1111115555,"888888","NA",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(order));
		when(or.save(order)).thenReturn(order);
		assertEquals("The order with ID -> "+order.getOrderId()+" is cancelled successfully",oc.cancelOrder(order));
		assertEquals("Cancelled",order.getStatus());
	}
	@Test
	public void getMyOrdersTest(){
		when(or.findAll()).thenReturn(Stream.of(
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
				new orderDetails("a","au@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning")
		).collect(Collectors.toList()));
		assertEquals(2,oc.getMyOrders("z@gmail.com").size());
	}
//	@Test
//	public void getPendingOrdersTest(){
//		when(or.findAll()).thenReturn(Stream.of(
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","au@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning")
//		).collect(Collectors.toList()));
//		assertEquals(1,oc.getPendingOrders().size());
//	}
//	@Test
//	public void getCompletedOrdersTest(){
//		when(or.findAll()).thenReturn(Stream.of(
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","au@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning")
//		).collect(Collectors.toList()));
//		assertEquals(2,oc.getCompletedOrders().size());
//	}
//	@Test
//	public void getCanceledOrdersTest(){
//		when(or.findAll()).thenReturn(Stream.of(
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","z@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","au@gmail.com","gwen","pack_1",1111115555,"888888","Completed",new Car(1,"BMW","X5"),"careful cleaning"),
//				new orderDetails("a","au@gmail.com","gwen","pack_1",1111115555,"888888","Cancelled",new Car(1,"BMW","X5"),"careful cleaning")
//		).collect(Collectors.toList()));
//		assertEquals(1,oc.getCancelledOrders().size());
//	}
	@Test
	public void assignWasherAlreadyExistsExceptionTest(){
		orderDetails order = new orderDetails("a","z@gmail.com","hee","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		orderDetails ExistingOrder = new orderDetails("a","z@gmail.com","Noor","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		when(or.findById("a")).thenReturn(Optional.of(ExistingOrder));
		when(or.save(ExistingOrder)).thenReturn(ExistingOrder);
		assertThrows(APIRequestException.class, ()-> oc.assignWasher("a",order.getWasherName()));
	}
	@Test
	public void assignWasherTest(){
		orderDetails order = new orderDetails("f","z@gmail.com","Noor","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		orderDetails ExistedOrder = new orderDetails("f","z@gmail.com","NA","pack_1",1111115555,"888888","Pending",new Car(1,"BMW","X5"),"careful cleaning");
		System.out.println(ExistedOrder.getWasherName());
		System.out.println(order.getOrderId());
		when(or.findById("f")).thenReturn(Optional.of(ExistedOrder));
		when(or.save(ExistedOrder)).thenReturn(ExistedOrder);
		String w = order.getWasherName();
		assertEquals(order.getWasherName(),oc.assignWasher("f",order.getWasherName()).getBody().getWasherName());
		assertEquals("Pending",ExistedOrder.getStatus());
		System.out.println(ExistedOrder.getWasherName());
		System.out.println(order.getWasherName());


	}
}
