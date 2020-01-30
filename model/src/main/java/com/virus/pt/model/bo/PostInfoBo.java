package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.PostInfo;
import com.virus.pt.model.dto.PostInfoDto;

import java.math.BigDecimal;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 11:04 下午
 * @email zzy.main@gmail.com
 */
public class PostInfoBo {
    public static PostInfo getPostInfo(short type, PostInfoDto postInfoDto, String content) {
        PostInfo postInfo = new PostInfo();
        postInfo.setInfoType(type);
        postInfo.setUkInfoId(postInfoDto.getId());
        postInfo.setRatingAverage(BigDecimal.valueOf(postInfoDto.getRatingAverage()));
        postInfo.setRatingNumRaters(Long.valueOf(postInfoDto.getRatingNumRaters()));
        if (postInfoDto.getSummary().length() > 1000) {
            postInfo.setSummary(postInfoDto.getSummary().substring(0, 1000) + "...");
        } else {
            postInfo.setSummary(postInfoDto.getSummary());
        }
        postInfo.setPosterUrl(postInfoDto.getPoster());
        postInfo.setContent(content);
        return postInfo;
    }
}
