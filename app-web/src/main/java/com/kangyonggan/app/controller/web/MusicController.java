package com.kangyonggan.app.controller.web;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.controller.BaseController;
import com.kangyonggan.app.model.Music;
import com.kangyonggan.app.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author kangyonggan
 * @since 1/11/19
 */
@Controller
@RequestMapping("music")
public class MusicController extends BaseController {

    @Autowired
    private MusicService musicService;

    /**
     * 音乐界面
     *
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping
    public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum, Model model) {
        List<Music> videos = musicService.findAllMusics(pageNum);
        PageInfo page = new PageInfo<>(videos);
        model.addAttribute("page", page);
        return "web/music/index";
    }

}
