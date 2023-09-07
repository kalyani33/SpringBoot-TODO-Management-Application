package com.example.todo.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.LoginDto;
import com.example.todo.dto.RegisterDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.exception.TodoAPIException;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterDto registerDto) {
		//Check whether username is already exist in database
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username already exists");
		}
		//Check if email already exists or not
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email already exist");
		}
		
		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setName(registerDto.getName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setUsername(registerDto.getUsername());
		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ADMIN");
//		role.setName("USER");
//		roleRepository.save(role);
		roles.add(role);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		return "User registered successfully!!";
	}

	@Override
	public JwtAuthResponse login(LoginDto loginDto) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail()
				, loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String token = jwtTokenProvider.generateToken(authenticate);
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail()
				, loginDto.getUsernameOrEmail());
		
		String role = null;
		if(userOptional.isPresent()) {
			User loggedInUser = userOptional.get();
			Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();
			if(optionalRole.isPresent()) {
				Role userRole = optionalRole.get();
				role = userRole.getName();
			}
		}
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setRole(role);
		
		return jwtAuthResponse;
	}

}
