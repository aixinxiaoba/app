package com.kangyonggan.app.service;

import com.kangyonggan.app.model.Music;
import com.kangyonggan.common.Params;

import java.util.List;

/**
 * @author kangyonggan
 * @since 1/11/19
 */
public interface MusicService {

    /**
     * 搜索视频
     *
     * @param params
     * @return
     */
    List<Music> searchMusics(Params params);

    /**
     * 保存音乐
     *
     * @param music
     */
    void saveMusic(Music music);

    /**
     * 获取音乐
     *
     * @param musicId
     * @return
     */
    Music getMusic(Long musicId);

    /**
     * 更新音乐
     *
     * @param music
     */
    void updateMusic(Music music);

    /**
     * 批量删除音乐
     *
     * @param musicIds
     */
    void deleteMusics(String musicIds);

    /**
     * 批量恢复音乐
     *
     * @param musicIds
     */
    void restoreMusics(String musicIds);

    /**
     * 查找所有音乐
     *
     * @param pageNum
     * @return
     */
    List<Music> findAllMusics(int pageNum);
}
