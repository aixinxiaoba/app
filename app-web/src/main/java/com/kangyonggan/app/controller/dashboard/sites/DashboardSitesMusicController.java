package com.kangyonggan.app.controller.dashboard.sites;

import com.kangyonggan.app.annotation.PermissionMenu;
import com.kangyonggan.app.annotation.Token;
import com.kangyonggan.app.controller.BaseController;
import com.kangyonggan.app.model.Music;
import com.kangyonggan.app.service.MusicService;
import com.kangyonggan.common.Page;
import com.kangyonggan.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author kangyonggan
 * @since 1/4/19
 */
@Controller
@RequestMapping("dashboard/sites/music")
public class DashboardSitesMusicController extends BaseController {

    private static final String PATH_ROOT = "dashboard/sites/music";

    @Autowired
    private MusicService musicService;

    /**
     * 音乐列表界面
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("SITES_MUSIC")
    public String index() {
        return PATH_ROOT + "/list";
    }

    /**
     * 音乐列表查询
     *
     * @return
     */
    @GetMapping("list")
    @PermissionMenu("SITES_MUSIC")
    @ResponseBody
    public Page<Music> list() {
        return new Page<>(musicService.searchMusics(getRequestParams()));
    }

    /**
     * 添加音乐
     *
     * @param model
     * @return
     */
    @GetMapping("create")
    @PermissionMenu("SITES_MUSIC")
    @Token(key = "createMusic")
    public String create(Model model) {
        model.addAttribute("music", new Music());
        return PATH_ROOT + "/form-modal";
    }

    /**
     * 保存音乐
     *
     * @param session
     * @param music
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    @PermissionMenu("SITES_MUSIC")
    @Token(key = "createMusic", type = Token.Type.CHECK)
    public Response save(HttpSession session, Music music) {
        // 不允许自动播放
        music.setContent(music.getContent().replace("auto=1", "auto=0"));

        music.setUserId(currentUserId(session));
        musicService.saveMusic(music);
        return Response.getSuccessResponse();
    }

    /**
     * 编辑音乐
     *
     * @param musicId
     * @param model
     * @return
     */
    @GetMapping("{musicId:[\\d]+}/edit")
    @PermissionMenu("SITES_MUSIC")
    @Token(key = "editMusic")
    public String edit(@PathVariable("musicId") Long musicId, Model model) {
        model.addAttribute("music", musicService.getMusic(musicId));
        return PATH_ROOT + "/form-modal";
    }

    /**
     * 更新音乐
     *
     * @param music
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    @PermissionMenu("SITES_MUSIC")
    @Token(key = "editMusic", type = Token.Type.CHECK)
    public Response update(Music music) {
        // 不允许自动播放
        music.setContent(music.getContent().replace("auto=1", "auto=0"));

        musicService.updateMusic(music);
        return Response.getSuccessResponse();
    }

    /**
     * 批量删除音乐
     *
     * @param musicIds 音乐ID
     * @return 响应
     */
    @GetMapping("delete")
    @PermissionMenu("SITES_MUSIC")
    @ResponseBody
    public Response delete(@RequestParam("musicIds") String musicIds) {
        musicService.deleteMusics(musicIds);
        return Response.getSuccessResponse();
    }

    /**
     * 批量恢复音乐
     *
     * @param musicIds 音乐ID
     * @return 响应
     */
    @GetMapping("restore")
    @PermissionMenu("SITES_MUSIC")
    @ResponseBody
    public Response restore(@RequestParam("musicIds") String musicIds) {
        musicService.restoreMusics(musicIds);
        return Response.getSuccessResponse();
    }
}
