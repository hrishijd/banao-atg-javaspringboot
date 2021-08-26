package com.authentication.loginsystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.authentication.loginsystem.configurations.MyUserDetailsService;
import com.authentication.loginsystem.filter.JwtRequestFilter;
import com.authentication.loginsystem.mailconfig.EmailService;
import com.authentication.loginsystem.models.AuthenticationRequest;
import com.authentication.loginsystem.models.AuthenticationResponse;
import com.authentication.loginsystem.models.JwtToken;
import com.authentication.loginsystem.models.StatusAndError;
import com.authentication.loginsystem.models.Users;
import com.authentication.loginsystem.repositories.UsersRepo;

@Controller
public class LoginController {
	@Autowired
	private UsersRepo usersRepo;
	@Autowired
	private MyUserDetailsService uds;
	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private EmailService emailService;
	@Autowired
	private com.authentication.loginsystem.configurations.JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	private String otpa;
	private String tempName;
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest ar) throws Exception
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
					(ar.getUsername(),ar.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new BadCredentialsException("wrong username or password").getClass());
		}
		final UserDetails ud=uds.loadUserByUsername(ar.getUsername());
		final String jwt= jwtUtil.generateToken(ud);
		final Users user=usersRepo.findByName(ar.getUsername());
		return ResponseEntity.ok(new AuthenticationResponse(user,new JwtToken(jwt)));
	}
	@RequestMapping("/users")
	private ResponseEntity<?> getUserInfo()
	{
		if(usersRepo.findByName(jwtRequestFilter.getUsername()).isAdmin())
		{
			return ResponseEntity.ok(usersRepo.findAll());
		}
		else
		{
			return ResponseEntity.ok(new StatusAndError("Error","You are not Authorized"));
		}
	}
	

	@RequestMapping("/")
	public ModelAndView goHome()
	{
		Users user=usersRepo.findByName(uds.userName);
		if(user.isAdmin())
		{
			List<Users> users=new ArrayList<>(usersRepo.findAll());
			ModelAndView mv=new ModelAndView("admin-home.jsp");
			mv.addObject("users",users);
			return mv;
		}
		else 
		{
			ModelAndView mv=new ModelAndView("home.jsp");
			mv.addObject("name",uds.userName);
			return mv;
		}
	}
	@RequestMapping("/login")
	public String goLogin()
	{
		return "login.jsp";
	}
	@RequestMapping("/signup")
	public String goSignup()
	{
		return "signup.jsp";
	}
	@RequestMapping(value="/createuser",method = RequestMethod.POST)
	public String createUser(@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("repassword") String repassword)
	{
		System.out.println(true);
		System.out.println(email+name+password+repassword);
		try {
			if(!password.contentEquals(repassword))
				throw new Exception("passwords not same");
			if(name==""||email==""||password=="")
				throw new Exception("cannot be null");
			String numbers = "0123456789";
			  
	        Random rndm_method = new Random();
	  
	        char[] otp = new char[4];
	  
	        for (int i = 0; i < 4; i++)
	        {
	            otp[i] =numbers.charAt(rndm_method.nextInt(numbers.length()));
	        }
	        String ot=new String(otp);
	        otpa=ot;
	        tempName=name;
			password=pe.encode(password);
			String to = email;
			String from = "Hey";
			String subject = name + ", welcome to LoginSystem";
			String body = name + ", Login System!\r\n" + "\r\n" + "\r\n"+"your otp is:"+ot;
			emailService.sendSimpleMessage(to, from, subject, body);
			boolean con=false;
			if(name.equalsIgnoreCase("hrishi")) con=true;
			
			Users user=new Users(0,email,name,password,con,false);
			usersRepo.save(user);
			
		    return "verify.jsp";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "signup.jsp";
		}
	}
	@RequestMapping(value="/otpcheck", method=RequestMethod.POST )
	public ModelAndView goverify(@RequestParam("otp") String otp)
	{
		String message="user created ";
		Users us=usersRepo.findByName(tempName);
		if(otp.equals(otpa))
		{
			message=message+"with ";
			us.setVerified(true);
			usersRepo.save(us);
		}
		else
		{
			message=message+"without ";
		}
		
		message= message +"verification";
		ModelAndView model=new ModelAndView("message.jsp");
		model.addObject("message", message);
		return model;
	}
}
