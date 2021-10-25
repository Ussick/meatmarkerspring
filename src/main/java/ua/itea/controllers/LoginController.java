package ua.itea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.daoImpl.Authorizator;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private boolean showAForm = true;
    private boolean isAccessDenied;
    private boolean isBlocked;
    private long timeOfLock;
    private int countLogins;
    private long rez;
    private Authorizator auth;

    @Autowired
    public void setAuth(Authorizator auth) {
        this.auth = auth;
    }

    @RequestMapping(value = ("/login"), method = RequestMethod.GET)
    public String getLoginView(ModelMap modelmap) {
        modelmap.addAttribute("showAForm", showAForm);
        modelmap.addAttribute("isAccessDenied", isAccessDenied);
        modelmap.addAttribute("isBlocked", isBlocked);
        return "login";
    }

    @RequestMapping(value = ("/login"), method = RequestMethod.POST)
    public String login(@RequestParam (name = "login") String login,
                        @RequestParam (name = "password") String password,
                        ModelMap modelmap, HttpSession session) {

            String loginFromSession = (String) session.getAttribute("authorized");

            if ((login == null && password == null)) {
            } else {
                String userName = auth.isAuthorized(login, password);
                if ((userName != null)) {
                    timeOfLock = 0;
                    showAForm = false;
                    isAccessDenied = false;
                    isBlocked = false;
                    session.setAttribute("authorized", userName);
                    loginFromSession = userName;
                    modelmap.addAttribute("result", "Access granted!");
                    modelmap.addAttribute("loginFromSession", loginFromSession);
                } else {
                    isAccessDenied = true;
                    countLogins++;
                    if (countLogins == 3) {
                        timeOfLock = System.currentTimeMillis();
                    }
                    if (countLogins >= 3) {
                        rez = ((timeOfLock + 10000) - System.currentTimeMillis()) / 1000;
                        if (rez > 0) {
                            isBlocked = true;
                            showAForm = false;
                            String block = "You locked for " + rez + " seconds";
                            modelmap.addAttribute("block", block);
                        } else {
                            timeOfLock = 0;
                            countLogins = 0;
                            rez = 0;
                            isBlocked = false;
                            showAForm = true;
                        }
                    }
                    modelmap.addAttribute("accessDenied", "Access denied. countLogins: " + countLogins);
                }
            }

        modelmap.addAttribute("isBlocked", isBlocked);
        modelmap.addAttribute("showAForm", showAForm);
        modelmap.addAttribute("isAccessDenied", isAccessDenied);
        return "login";
    }

    @RequestMapping(value = ("/login"), params = {"logout"}, method = RequestMethod.POST)
    public String logout(ModelMap modelmap, HttpSession session) {
        session.setAttribute("authorized", null);
        isAccessDenied = true;
        showAForm = true;
        isBlocked = false;
        modelmap.addAttribute("isBlocked", isBlocked);
        modelmap.addAttribute("showAForm", showAForm);
        modelmap.addAttribute("isAccessDenied", isAccessDenied);
        return "login";
    }
}
