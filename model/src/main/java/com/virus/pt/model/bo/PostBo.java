package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Post;
import com.virus.pt.model.dto.PostDto;
import com.virus.pt.model.dto.PostInfoDto;
import com.virus.pt.model.dto.SeriesDto;
import com.virus.pt.model.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/1 11:33 上午
 * @email zzy.main@gmail.com
 */
public class PostBo {
    public static PostDto getPostDto(Post post, String username,
                                     List<SeriesDto> seriesDtoList, PostInfoDto douban, PostInfoDto imdb) {
        PostDto postDTO = new PostDto();
        BeanUtils.copyFieldToBean(post, postDTO);
        postDTO.setUserAuthId(post.getFkUserAuthId());
        postDTO.setCreate(post.getCreated().getTime());
        postDTO.setModify(post.getModified().getTime());
        if (StringUtils.isNotBlank(username)) {
            postDTO.setUsername(username);
        }
        if (seriesDtoList.size() > 0) {
            postDTO.setSeriesList(seriesDtoList);
        }
        if (douban != null) {
            postDTO.setDouban(douban);
        }
        if (imdb != null) {
            postDTO.setImdb(imdb);
        }
        return postDTO;
    }
}
