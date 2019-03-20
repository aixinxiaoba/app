package com.kangyonggan.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.constants.YesNo;
import com.kangyonggan.app.model.Music;
import com.kangyonggan.app.service.MusicService;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.common.BaseService;
import com.kangyonggan.common.Params;
import com.kangyonggan.common.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2019/3/20
 */
@Service
public class MusicServiceImpl extends BaseService<Music> implements MusicService {

    @Override
    public List<Music> searchMusics(Params params) {
        Example example = new Example(Music.class);
        Query query = params.getQuery();

        Example.Criteria criteria = example.createCriteria();
        String userId = query.getString("userId");
        if (StringUtils.isNotEmpty(userId)) {
            criteria.andEqualTo("userId", userId);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("music_id DESC");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    public void saveMusic(Music music) {
        myMapper.insertSelective(music);
    }

    @Override
    public Music getMusic(Long musicId) {
        return myMapper.selectByPrimaryKey(musicId);
    }

    @Override
    public void updateMusic(Music music) {
        myMapper.updateByPrimaryKeySelective(music);
    }

    @Override
    public void deleteMusics(String musicIds) {
        if (StringUtils.isEmpty(musicIds)) {
            return;
        }
        String[] ids = musicIds.split(",");
        Example example = new Example(Music.class);
        example.createCriteria().andIn("musicId", Arrays.asList(ids));

        Music music = new Music();
        music.setIsDeleted(YesNo.YES.getCode());

        myMapper.updateByExampleSelective(music, example);
    }

    @Override
    public void restoreMusics(String musicIds) {
        if (StringUtils.isEmpty(musicIds)) {
            return;
        }
        String[] ids = musicIds.split(",");
        Example example = new Example(Music.class);
        example.createCriteria().andIn("musicId", Arrays.asList(ids));

        Music music = new Music();
        music.setIsDeleted(YesNo.NO.getCode());

        myMapper.updateByExampleSelective(music, example);
    }

    @Override
    public List<Music> findAllMusics(int pageNum) {
        Example example = new Example(Music.class);
        example.createCriteria().andEqualTo("isDeleted", YesNo.NO.getCode());

        example.setOrderByClause("music_id DESC");

        PageHelper.startPage(pageNum, 12);
        return myMapper.selectByExample(example);
    }
}
