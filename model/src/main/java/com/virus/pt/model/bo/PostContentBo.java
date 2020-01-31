package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.PostContent;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:09 下午
 * @email zzy.main@gmail.com
 */
public class PostContentBo {
    public static PostContent
    getPostContent(String content) {
        PostContent postContent = new PostContent();
        postContent.setContent(content);
        return postContent;
    }
}
