package com.trading.platform.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.platform.entity.OrderDetails;
import com.trading.platform.model.Order;
import com.trading.platform.model.OrderType;
import com.trading.platform.service.PlatformService;
import com.trading.platform.workflows.AddOrderWorkflow;

@WebMvcTest(PlatformController.class)
public class PlatformControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@SuppressWarnings("removal")
	@MockBean
	private PlatformService platformService;

	@MockBean
	private AddOrderWorkflow trandingWorkflows;

	Order order;
	OrderDetails orderDetails;

	@BeforeEach
	public void setup() {

		order = buildOrderObject();
		orderDetails = buildOrderDetailsObject();
	}

	private Order buildOrderObject() {
		Order order = new Order();
		order.setOrder_type(OrderType.BUY);
		order.setQuantity(10);

		return order;
	}

	private OrderDetails buildOrderDetailsObject() {
		OrderDetails addOrder = new OrderDetails();
		addOrder.setCode("200");
		addOrder.setMessage("Successfull");

		return addOrder;
	}

	@Test
	public void givenOrder_whenAddOrder_thenReturnSuccess() throws Exception {

		given(platformService.addOrder(order)).willReturn(orderDetails);

		mockMvc.perform(MockMvcRequestBuilders.post("/trade/api/addOrder").content(asJsonString(order))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
