package com.kangyonggan.app.controller.web;

import com.kangyonggan.app.constants.AppConstants;
import com.kangyonggan.app.constants.AppSource;
import com.kangyonggan.app.controller.BaseController;
import com.kangyonggan.app.model.LoginLog;
import com.kangyonggan.app.model.User;
import com.kangyonggan.app.service.LoginLogService;
import com.kangyonggan.app.service.UserService;
import com.kangyonggan.app.util.Digests;
import com.kangyonggan.app.util.Encodes;
import com.kangyonggan.app.util.RedisSession;
import com.kangyonggan.common.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 登录、注销
 *
 * @author kangyonggan
 * @since 8/8/18
 */
@Controller
@RequestMapping("/")
@Log4j2
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 登录界面
     *
     * @param redirectUrl
     * @param model
     * @return
     */
    @GetMapping("login")
    public String index(@RequestParam(value = "redirectUrl", required = false, defaultValue = "") String redirectUrl,
                        Model model) {
        model.addAttribute("redirectUrl", redirectUrl);
        return "web/login/index";
    }

    /**
     * 登录
     *
     * @param captcha
     * @param user
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Response login(@RequestParam(value = "captcha", required = false, defaultValue = "") String captcha, User user) {
        Response response = Response.getSuccessResponse();

        Integer errCount = RedisSession.get("ERR_COUNT", Integer.class);
        if (errCount == null) {
            errCount = 0;
        }
        if (errCount > 0) {
            String realCaptcha = RedisSession.getString(AppConstants.KEY_CAPTCHA);

            // 清除验证码
            RedisSession.delete(AppConstants.KEY_CAPTCHA);
            if (!captcha.equalsIgnoreCase(realCaptcha)) {
                return response.failure("验证码错误或已失效，请重新获取");
            }
        }

        User dbUser = userService.findUserByEmail(user.getEmail());
        if (dbUser == null) {
            RedisSession.put("ERR_COUNT", ++errCount);
            response.put("errCount", errCount);
            return response.failure("电子邮箱不存在");
        }
        if (dbUser.getIsDeleted() == 1) {
            RedisSession.put("ERR_COUNT", ++errCount);
            response.put("errCount", errCount);
            return response.failure("电子邮箱已被锁定");
        }

        byte[] salt = Encodes.decodeHex(dbUser.getSalt());
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        String password = Encodes.encodeHex(hashPassword);
        if (!dbUser.getPassword().equals(password)) {
            RedisSession.put("ERR_COUNT", ++errCount);
            response.put("errCount", errCount);
            log.error("密码错误, ip:{}, user:{}", getIpAddress(), user);
            return response.failure("密码错误");
        }

        // 把登录信息放入redis
        String jsessionid = RedisSession.saveUser(dbUser);

        // 保存登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setJsessionid(jsessionid);
        loginLog.setUserId(dbUser.getUserId());
        loginLog.setIpAddress(getIpAddress());
        loginLog.setAppSource(AppSource.PC.getCode());
        loginLog.setEmail(dbUser.getEmail());

        loginLogService.saveLoginLog(loginLog);
        RedisSession.put("ERR_COUNT", 0);
        return response;
    }

    /**
     * 注销
     *
     * @return
     */
    @GetMapping("logout")
    public String logout() {
        RedisSession.removeUser();
        return "redirect:/";
    }
}
