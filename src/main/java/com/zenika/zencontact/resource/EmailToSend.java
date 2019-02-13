package com.zenika.zencontact.ressource;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.zenika.zencontact.email.EmailService;

public class EmailToSend extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Email email = new Gson().fromJson(request.getReader(),Email.class);
        EmailService.getInstance().sendEmail(email);
    }
}