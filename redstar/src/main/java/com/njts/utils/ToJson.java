package com.njts.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public  class ToJson {
  public   static  void  toJson(HttpServletResponse response,Result result){
         String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.print(jsonStr);
        out.flush();
        out.close();
    }

}
