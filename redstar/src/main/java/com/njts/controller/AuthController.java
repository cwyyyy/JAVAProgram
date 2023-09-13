package com.njts.controller;

import com.njts.pojo.Auth;
import com.njts.service.AuthService;
import com.njts.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

 @GetMapping("/auth-tree")
public Result authTree(){
     List<Auth> allAuthTree = authService.allAuthTree();
return  Result.ok(allAuthTree);
 }


}
