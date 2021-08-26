package com.authentication.loginsystemTesting;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMapAdapter;

import com.authentication.loginsystem.controllers.LoginController;
import com.authentication.loginsystem.models.AuthenticationRequest;
import com.authentication.loginsystem.models.JwtToken;
import com.authentication.loginsystem.models.Users;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int randomServerPort;
	@Test
	public void testApi() throws URISyntaxException
	{
		final String baseUrl = "http://localhost:"+randomServerPort+"/authenticate";
        URI uri = new URI(baseUrl);
        AuthenticationRequest authReq = new AuthenticationRequest("hrishi", "hrishi");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuthenticationRequest> request=new HttpEntity<AuthenticationRequest>(authReq, headers);
        ResponseEntity<JwtToken> result = this.restTemplate.postForEntity(uri,request , JwtToken.class);
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", result.getBody().getJwtToken());
        HttpEntity request1=new HttpEntity(header);
        ResponseEntity<List<Users>> result1 = restTemplate.exchange("http://localhost:"+randomServerPort+"/users", HttpMethod.GET, request1, new ParameterizedTypeReference<List<Users>>(){});
        Assert.assertEquals(400, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().getJwtToken().isEmpty());
        Assert.assertEquals(400, result1.getStatusCodeValue());
        Assert.assertEquals(true, result1.getBody().isEmpty());
	}
}
