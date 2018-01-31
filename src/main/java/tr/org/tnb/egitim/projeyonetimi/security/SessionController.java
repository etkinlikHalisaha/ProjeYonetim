package tr.org.tnb.egitim.projeyonetimi.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.CommonDao;

@Controller("sessionController")
@Scope("session")
public class SessionController implements Serializable {


    @Autowired
    private transient CommonDao commonDao;

    public boolean isSessionExists() {
        return SecurityContextHolder.getContext().getAuthentication() != null
            && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
            && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
    }

    private User user;

    public User getUser() {
        if (user == null) {
            try {
                user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } catch (Exception e) {
                //logger.warn("", e);
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDbUser() {
        return (User) commonDao.getById(this.getUser().getId(), User.class);
    }

    @PostConstruct
    public void init() {
        getUser();
    }

}