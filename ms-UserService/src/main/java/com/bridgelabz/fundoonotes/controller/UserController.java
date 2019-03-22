package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.model.UserDetails;
import com.bridgelabz.fundoonotes.service.UserService;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping("/user")
public class UserController {
	// @Autowired
	// @Qualifier("userValidator")
	// private Validator validator;

	// @InitBinder
	// private void initBinder(WebDataBinder binder) {
	// binder.setValidator( validator);
	// }
	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody UserDetails user, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("Invalid entry!!! Please enter valid details", HttpStatus.BAD_REQUEST);
		}
		UserDetails newUser = userService.register(user, request);
		return new ResponseEntity<UserDetails>(newUser, HttpStatus.OK);

	}

	

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDetails user, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String token = userService.login(user, request, response);
			if (token != null)
				response.setHeader("token", token);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/activationstatus/{token:.+}")
	public ResponseEntity<String> activateUser(@PathVariable String token, HttpServletRequest request) {
		UserDetails user = userService.activateUser(token, request);
		if (user != null) {
			return new ResponseEntity<String>("User has been activated successfully", HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/update")
	public ResponseEntity<String> update(@RequestHeader("token") String token, @RequestBody UserDetails user,
			HttpServletRequest request) {
		try {
			if (userService.update(token, user, request) != null)
				return new ResponseEntity<String>("User Succesfully updated", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Dinied In Updating", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("pls provide details correctly", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> delete(@RequestHeader("token") String token, HttpServletRequest request) {
		try {
			if (userService.delete(token, request) != false) {
				return new ResponseEntity<String>("User Succesfully deleted", HttpStatus.FOUND);
			} else
				return new ResponseEntity<String>("Dinied In Deleting", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>("pls provide details correctly", HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestBody UserDetails user, HttpServletRequest request) {

		boolean newUser = userService.forgotPassword(user, request);
		if (newUser == false) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/resetpassword/{token:.+}")
	public ResponseEntity<?> resetPassword(@RequestBody UserDetails user, @PathVariable("token") String token,
			HttpServletRequest request) {
		UserDetails user1 = userService.resetPassword(user, token, request);
		if (user1 != null) {
			return new ResponseEntity<Void>( HttpStatus.OK);

		} else {
			return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/uploadimage/{token:.+}")
	public ResponseEntity<?> uploadFile(@PathVariable ("token")String token,@RequestParam ("file")
	MultipartFile  imageUpload ) throws IOException {	      
		if( userService.uploadImage(token,imageUpload)!=null)
			return new ResponseEntity<String>("Successfully uploaded", HttpStatus.OK);

		else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.NOT_FOUND);

}
	
	@GetMapping("uploadimage")
    public ResponseEntity<?> downloadFile(@RequestHeader("token") String token) {
        UserDetails user = userService.getImage(token);
        if(user!=null)
			return new ResponseEntity<UserDetails>(user,HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot download the image", HttpStatus.CONFLICT);
}
	
	@DeleteMapping("uploadimage")
    public ResponseEntity<?> deleteFile(@RequestHeader("token") String token) {
		UserDetails user = userService.deleteImage(token);
        if(user!=null)
			return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
}
	
	
	@GetMapping("colaborator")
	public ResponseEntity<?> colaborator(@RequestHeader("token") String token, HttpServletRequest request) {
		UserDetails user = userService.colaborator(token, request);
		if (user != null)
			return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
		return new ResponseEntity<String>("user not found", HttpStatus.NOT_FOUND);
	}
	
	
	
	@GetMapping(value = "verifyemail/{emailId:.+}")
	public ResponseEntity<?> verifyEmail(@RequestHeader("token") String token,@PathVariable("emailId") String email, HttpServletRequest request) {
		UserDetails newUser=userService.verifyEmail(token,email,request);
		if (newUser!=null)
			return new ResponseEntity<UserDetails>(newUser,HttpStatus.OK);
		return new ResponseEntity<String>("user not found", HttpStatus.NOT_FOUND);
}
	
	@GetMapping("collaborateduser/{userId}")
    public ResponseEntity<?> getCollaboratedUser(@PathVariable("userId") int userId) {
		UserDetails user = userService.collaboratedUser(userId);
        if(user!=null)
			return new ResponseEntity<UserDetails>(user,HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
}

}
