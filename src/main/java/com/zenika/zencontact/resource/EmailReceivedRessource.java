package com.zenika.zencontact.ressource;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name="EmailReceivedRessource", value="_ah/mail/*")
@ServletSecurity(@HttpConstraint(rolesAllowed={"admin"}))
public class EmailReceivedRessource extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

    }
}