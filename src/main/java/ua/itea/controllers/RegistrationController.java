package ua.itea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.dao.UserDao;
import ua.itea.daoImpl.DaoFactory;
import ua.itea.model.User;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {
    private boolean showForm = true;
    private boolean isError = false;
    private User user;

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }

    @RequestMapping(value = ("/registration"), method = RequestMethod.GET)
    public String getRegistrationView(ModelMap modelMap) {
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("isError", isError);
        modelMap.addAttribute("showform", showForm);
        return "registration";
    }

    @RequestMapping(value = ("/registration"), method = RequestMethod.POST)
    public String registration(@RequestParam(name = "login") String login,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "passwordRepeat") String passwordRepeat,
                               @RequestParam(name = "gender", required = false) String gender,
                               @RequestParam(name = "region") String region,
                               @RequestParam(name = "comment") String comment,
                               @RequestParam(name = "browser", required = false) String browser,
                               ModelMap modelMap, HttpSession session) {

        isError = false;
        String regLog = "^([a-zA-Z0-9_]{2,}[\\.])*[a-zA-Z0-9_]{2,}[@][a-zA-Z]{2,}[\\.a-zA-Z]{3,}$";
        String reg1 = "^[\\w\\W]{8,}$";
        String reg2 = "^[\\w\\W]*[A-ZА-Я]+[\\w\\W]*$";
        String reg3 = "^[\\w\\W]*[0-9]+[\\w\\W]*$";
        String errorText = "<ul>";

        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);
        user.setPasswordRepeat(passwordRepeat);
        user.setGender(gender);
        user.setRegion(region);
        user.setComment(comment);

        if (login == null || login.isEmpty()) {
            isError = true;
            errorText += "<li>Login is empty!</li>";
        } else {
            if (Pattern.matches(regLog, login)) {
            } else {
                isError = true;
                errorText += "<li>Login is not valid!</li>";
            }
        }

        if (name == null || name.isEmpty()) {
            isError = true;
            errorText += "<li>Name is empty!</li>";
        }

        if (password == null || password.isEmpty()) {
            isError = true;
            errorText += "<li>Password is empty!</li>";
        } else {
            if (Objects.equals(password, passwordRepeat)) {
                if (Pattern.matches(reg1, password) && Pattern.matches(reg2, password) && Pattern.matches(reg3, password)) {
                } else {
                    isError = true;
                    errorText += "<li>Password have to be more then 8 symbols with minimum 1 capital letter and 1 number !</li>";
                }
            } else {
                isError = true;
                errorText += "<li>Password and RepeatPassword are not equal!</li>";
            }
        }

        if (gender == null || gender.isEmpty()) {
            isError = true;
            errorText += "<li>Choose your gender!</li>";
        }

        if (comment == null || comment.isEmpty()) {
            isError = true;
            errorText += "<li>Fill in your comment!</li>";
        }

        if (browser == null || browser.isEmpty() ) {
            isError = true;
            errorText += "<li>Amigo Browser has to be chosen!</li>";
        }

        errorText += "</ul>";

        if (!isError) {
            UserDao de = DaoFactory.getInstance().getUserDAO();

            de.addUser(user);
            showForm = false;
            modelMap.addAttribute("showform", showForm);
            modelMap.addAttribute("isError", isError);
            modelMap.addAttribute("result", "Registration succeeded!");
            showForm = true;
            isError = false;
        } else {
            modelMap.addAttribute("isError", isError);
            modelMap.addAttribute("result", errorText);
            getRegistrationView(modelMap);
        }
        return "registration";
    }
}
