package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Post;
import com.virus.pt.model.dto.PostQualityDto;
import com.virus.pt.model.util.BeanUtils;
import com.virus.pt.model.vo.PostReleaseVo;
import org.apache.commons.lang3.StringUtils;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 4:22 下午
 * @email zzy.main@gmail.com
 */
public class PostReleaseBo {

    public static PostQualityDto getPostQuality(PostReleaseVo postReleaseVo) {
        PostQualityDto postQualityDto = new PostQualityDto();
        BeanUtils.copyFieldToBean(postReleaseVo, postQualityDto);
        return postQualityDto;
    }

    public static Post getPost(PostReleaseVo postReleaseVo, long userId, String qualityJson) {
        Post post = new Post();
        // 处理豆瓣或imdb Id
        if (postReleaseVo.getPostInfoType() == 10) {
            post.setImdbId(postReleaseVo.getPostInfoId());
        } else {
            post.setDoubanId(postReleaseVo.getPostInfoId());
        }
        post.setFkPostCategoryId(postReleaseVo.getPostCategoryId());
        post.setTitle(postReleaseVo.getTitle());
        post.setSubtitle(postReleaseVo.getSubtitle());
        post.setQuality(qualityJson);
        return post;
    }
}
