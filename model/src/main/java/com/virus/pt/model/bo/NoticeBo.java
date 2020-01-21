package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Notice;
import com.virus.pt.model.dto.NoticeDto;
import com.virus.pt.model.util.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 12:16 下午
 * @email zzy.main@gmail.com
 */
public class NoticeBo {

    public static List<NoticeDto> getNoticeDtoList(List<Notice> noticeList) {
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        noticeList.forEach(notice -> {
            NoticeDto noticeDTO = new NoticeDto();
            BeanUtils.copyFieldToBean(notice, noticeDTO);
            noticeDTO.setCreate(notice.getCreated().getTime());
            noticeDTO.setModify(notice.getModified().getTime());
            noticeDtoList.add(noticeDTO);
        });
        return noticeDtoList;
    }
}
