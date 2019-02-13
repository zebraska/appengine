package com.zenika.zencontact.resource;

import com.zenika.zencontact.fetch.PartnerBirthdateService;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserRepository;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import com.google.appengine.api.memcache.*;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.text.*;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResource", value = "/api/v0/users")
public class UserResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
        MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
        List<User> users = (List<User>) cache.get("users");
        if(users == null){
          users = UserDaoObjectify.getInstance().getAll();
          cache.put("users", users, Expiration.byDeltaSeconds(240),
          MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(new Gson()
          .toJsonTree(users).getAsJsonArray());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
      MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
      User user = new Gson().fromJson(request.getReader(), User.class);
      String birthdate = PartnerBirthdateService.getInstance().findBirthdate(
        user.firstname,
        user.lastname,
      )

    if(birthdate != null){
      try{
        user.birthdate(new SimpleDateFormat("yyyy-MM-dd")).parse(birthdate));
      }catch(ParseException e){}
    }
    user.id(UserDaoObjectify.getInstance().save(user));
    cache.delete("users");
    response.setContentType("application/json; charset=utf-8");
    response.setStatus(201);
    response.getWriter().println(new Gson().toJson(user));
  }
}
