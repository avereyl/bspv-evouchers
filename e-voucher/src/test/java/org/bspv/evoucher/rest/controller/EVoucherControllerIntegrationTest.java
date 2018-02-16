package org.bspv.evoucher.rest.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import org.bspv.evoucher.EVoucherApplication;
import org.bspv.evoucher.config.listener.HsqldbServletContextListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@Slf4j
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)// leads to ugly naming but useful !
@ContextConfiguration(classes = { EVoucherApplication.class })
@RunWith(SpringRunner.class)
public class EVoucherControllerIntegrationTest implements InitializingBean, DisposableBean {

	private static final String AUTH_COOKIE_NAME = "AuthorizationCookie";

	/**
	 * The {@link MockServletContext} does not support listener registration, so the
	 * HSQLDB cannot be started automatically using the
	 * {@link HsqldbServletContextListener}.
	 * 
	 * Then we inject this listener and call start and stop in the method
	 * implemented from {@link InitializingBean} and {@link DisposableBean} as they
	 * are called before/after Junit annotation with same name ;-).
	 */
	@Autowired
	private HsqldbServletContextListener hsqldbServletContextListener;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	private volatile Cookie authCookie = new Cookie(AUTH_COOKIE_NAME, "");

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		hsqldbServletContextListener.contextInitialized(null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		hsqldbServletContextListener.contextDestroyed(null);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	@Test
	public void t01_givenWac_whenServletContext_thenItProvidesEVoucherController() {
		ServletContext servletContext = wac.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean(EVoucherController.class));
	}

	@Test
	public void t02_givenRootURI_whenMockMVC_thenVerifyResponseStatus() throws Exception {
		this.mockMvc.perform(get("/"))
				// .andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void t03_givenEVouchersURI_whenMockMVC_thenVerify403Status() throws Exception {
		this.mockMvc.perform(get("/eVouchers/"))
				// .andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	public void t04_givenLoginURI_whenMockMVC_thenResponseOkAndJwtCookie() throws Exception {
		// @formatter:off
		MvcResult result = this.mockMvc.perform(post("/login")
				.content("{\"username\":\"gbillaud\", \"password\":\"key\"}"))
				.andExpect(status().isOk())
				.andExpect(cookie().exists(AUTH_COOKIE_NAME))
				.andExpect(cookie().secure(AUTH_COOKIE_NAME, true))
				.andReturn();
		// @formatter:on
		authCookie.setValue(result.getResponse().getCookie(AUTH_COOKIE_NAME).getValue());
	}
	
	// FIXME use TestNG to be able to use a field value set by a previous test !
	// plus handle dependencies between tests or tests groups
	@Test
	public void t05_givenEVouchersURI_whenMockMVC_thenVerify200Status() throws Exception {
		this.mockMvc.perform(get("/eVouchers/").cookie(authCookie)).andExpect(status().isOk());
	}

}
