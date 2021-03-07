package com.security.springBoot.controller;
import com.security.springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.security.springBoot.models.Role;
import com.security.springBoot.models.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

	private long id;

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	final private UserDetailsService service;
	@Autowired
	private final UserService userService;

	public UserController(@Qualifier("userDetailsServiceImpl") UserDetailsService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}


	@GetMapping("user")
	public String userPage(Principal principal,  Model model){
		model.addAttribute("user", userService.getUserByName(principal.getName()));
		return "user";
	}

	@GetMapping("admin")
	public String adminPage(Model model, Principal principal){
		model.addAttribute("user", new User());
		model.addAttribute("admin", userService.getUserByName(principal.getName()));
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		model.addAttribute("roleList", roles);
		model.addAttribute("userList", userService.userList());
		return "admin";
	}

	@PostMapping("/admin")
	public String create(@ModelAttribute("user") User user){
		userService.addUser(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/logout")
	public String logout(){
		return "logout";
	}

	@PostMapping("logout")
	public String logoutURL(){
		return "redirect:/logout";
	}

	@RequestMapping(value = "/admin/remove", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String remove(User user){
		user.setId(id);
		userService.deleteUser(id);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/getUser")
	@ResponseBody
	public User getUser(long id){
		User user = userService.getUserById(id);
		this.id = id;
		return user;
	}

	@RequestMapping(value = "/admin/edit", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(User user) {
		user.setId(id);
		userService.updateUser(user);

		return "redirect:/admin";
	}

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

}
