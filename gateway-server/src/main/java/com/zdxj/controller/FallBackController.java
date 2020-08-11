package com.zdxj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;

@RestController
public class FallBackController {

    @RequestMapping("fallback")
    public Result<Object> fallback(){
        return Result.failed("服务不可用，请稍后重试");
    }
}
