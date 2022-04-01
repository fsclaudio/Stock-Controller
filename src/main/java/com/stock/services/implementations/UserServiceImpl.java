package com.stock.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.stock.dto.UserDTO;
import com.stock.dto.UserFormDTO;
import com.stock.entities.Role;
import com.stock.entities.User;
import com.stock.exceptions.ResourceNotFoundException;
import com.stock.repositories.RoleRepository;
import com.stock.repositories.UserRepository;
import com.stock.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDTO saveUser(@Valid UserFormDTO body) {
		return mapper.map(userRepo.save(mapper.map(body, User.class)), UserDTO.class);
	}

	@Override
	public UserDTO updateUser(@PathVariable Long id, @Valid UserFormDTO body) {
		User chosenUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));

		BeanUtils.copyProperties(body, chosenUser, "id");

		return mapper.map(userRepo.save(chosenUser), UserDTO.class);
	}

	@Override
	public Role saveRole(@Valid Role role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String name, String roleName) {
		Role role = roleRepo.findByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role" + roleName + " not found."));
		User user = userRepo.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("User" + name + " not found."));

		user.getRoles().add(role);

		userRepo.save(user);
	}

	@Override
	public UserDTO getUser(String name) {
		User user = userRepo.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("User" + name + " not found."));
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public Page<UserDTO> listUsers(PageRequest pageRequest) {

		Page<User> users = userRepo.findAll(pageRequest);

		List<UserDTO> list = users.getContent().stream().map(User -> mapper.map(User, UserDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<>(list);
	}

	@Override
	public void deleteUser(@PathVariable Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));

		userRepo.delete(user);
	}

}
