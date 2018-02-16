package org.bspv.evoucher.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


//@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EVoucherControllerIntegrationTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesEVoucherController() {
	    ServletContext servletContext = wac.getServletContext();
	    Assert.assertNotNull(servletContext);
	    Assert.assertTrue(servletContext instanceof MockServletContext);
	    Assert.assertNotNull(wac.getBean(EVoucherController.class));
	}

	@Test
	public void givenRootURI_whenMockMVC_thenVerifyResponseStatus() throws Exception {
	    this.mockMvc.perform(get("/"))
	      .andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void givenEVouchersURI_whenMockMVC_thenVerify403Status() throws Exception {
	    this.mockMvc.perform(get("/eVouchers/"))
	      .andDo(print()).andExpect(status().isForbidden());
	}
}
