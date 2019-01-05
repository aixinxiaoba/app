package com.kangyonggan.app.controller.api;

import com.kangyonggan.app.controller.BaseController;
import com.kangyonggan.app.model.Novel;
import com.kangyonggan.app.service.NovelService;
import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.common.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019/1/5 0005
 */
@RestController
@RequestMapping("api/terminal")
public class ApiTerminalController extends BaseController {

    @Value("${app.terminal.whiteList}")
    private String whiteList;

    @Autowired
    private NovelService novelService;

    /**
     * 更新小说
     *
     * @param novelIds
     * @return
     */
    @GetMapping("pullNovel")
    public Response pullNovel(@RequestParam(value = "novelIds", required = false, defaultValue = "") String novelIds) {
        if (StringUtils.isNotEmpty(whiteList) && !whiteList.contains(getIpAddress())) {
            return Response.getFailureResponse("不在ip白名单中");
        }

        if (StringUtils.isEmpty(novelIds)) {
            // 没指定小说id，那就更新全部小说
            List<Novel> novels = novelService.findAllNovels();
            StringBuilder ids = new StringBuilder();
            for (Novel novel : novels) {
                ids.append(novel.getNovelId()).append(",");
            }
            if (ids.length() > 0) {
                ids.deleteCharAt(ids.lastIndexOf(","));
            }
            novelIds = ids.toString();
        }

        novelService.pullNovels(novelIds);

        return Response.getSuccessResponse();
    }

}
