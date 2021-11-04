package ru.folder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.folder.dao.UsersDao;
import ru.folder.forms.UserForm;
import ru.folder.models.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersControllerRequestNameUrlHandlerMapping {
    private UsersDao usersDao;
    @Autowired
    public UsersControllerRequestNameUrlHandlerMapping(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

//    @RequestMapping(path = "/users1", method = RequestMethod.GET)
//    public ModelAndView getAllUsers(){
//        List<User> userList = usersDao.findAll();
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("users");
//        modelAndView.addObject("usersFromServer", userList);
//
//        return modelAndView;
//    }

    @RequestMapping(path = "/users1", method = RequestMethod.GET)
    public ModelAndView getAllUsers(@RequestParam(value = "first_name", required = false) String firstName){
        List<User> userList = null;

        if (firstName != null){
            userList = usersDao.findAllByFirstName(firstName);
        } else {
            userList = usersDao.findAll();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("usersFromServer", userList);

        return modelAndView;
    }

    @RequestMapping(path = "/users1/{user-id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable("user-id") Long userId){
        Optional<User> userCandidate = usersDao.find(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        if (userCandidate.isPresent()) {
            modelAndView.addObject("usersFromServer", Arrays.asList(userCandidate.get()));
        }
        return modelAndView;
    }

    @RequestMapping(path = "/users1", method = RequestMethod.POST)
    public String addUser (UserForm userForm){
        System.out.println(userForm);
        User newUser = User.from(userForm);
        usersDao.save(newUser);
        return "redirect:/users";
    }
}
