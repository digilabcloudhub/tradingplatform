package com.trading.platform.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.platform.entity.OrderDetails;
import com.trading.platform.model.Order;
import com.trading.platform.service.PlatformService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatformController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlatformControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	  @SuppressWarnings("removal")
	@MockBean
	    private PlatformService platformService;

	    @Autowired
	    private ObjectMapper objectMapper;

	    Order order;
	    OrderDetails orderDetails;

	    @BeforeEach
	    public void setup(){

	    	order = buildOrderObject();

	    }

		private Order buildOrderObject() {
			Order order= new Order();
			order.setOrder_id(1L);
			order.setOrder_price(1.00);
			order.setQuantity(10);
			order.setTrade_id(2L);
			
			return order;
		}
		
		@Test
	    public void saveEmployeeTest() throws Exception{
	        // precondition
	        given(platformService.addOrder(any(Order.class))).willReturn(orderDetails);

	        // action
	        ResultActions response = mockMvc.perform(post("/api/employees")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(order)));

	        // verify
	        response.andDo(print()).
	                andExpect(status().isCreated())
	                .andExpect(jsonPath("$.orderId",
	                        is(order.getOrder_id())))
	                .andExpect(jsonPath("$.tradeId",
	                        is(order.getTrade_id())))
	                .andExpect(jsonPath("$.quantity",
	                        is(order.getQuantity())));
	    }

}
