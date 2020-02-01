package com.virus.pt.model.bo;


import com.virus.pt.model.dataobject.Torrent;
import com.virus.pt.model.dataobject.TorrentDiscount;
import com.virus.pt.model.dto.PeerDto;
import com.virus.pt.model.dto.TorrentDiscountDto;
import com.virus.pt.model.dto.TorrentDto;
import com.virus.pt.model.util.BeanUtils;
import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author intent
 * @date 2019/8/2 10:17
 */
public class TorrentBo {
    public static Torrent getTorrent(Torrent torrent, long userId,
                                     String fileName, String relativePath, long fileSize) {
        torrent.setFkUserAuthId(userId);
        torrent.setFileName(fileName);
        torrent.setFilePath(relativePath);
        torrent.setFileSize(fileSize);
        return torrent;
    }

    public static TorrentDto getTorrentDto(Torrent torrent, TorrentDiscount discount,
                                           Long uploadCount, Long completeCount, Long downloadCount) {
        TorrentDto torrentDTO = new TorrentDto();
        BeanUtils.copyFieldToBean(torrent, torrentDTO);
        torrentDTO.setUserAuthId(torrent.getFkUserAuthId());
        torrentDTO.setHash(Hex.encodeHexString(torrent.getUkInfoHash()));
        torrentDTO.setCreate(torrent.getCreated().getTime());
        torrentDTO.setModify(torrent.getModified().getTime());
        if (discount != null) {
            TorrentDiscountDto discountDTO = new TorrentDiscountDto();
            BeanUtils.copyFieldToBean(discount, discountDTO);
            torrentDTO.setDiscount(discountDTO);
        }
        PeerDto peerDto = new PeerDto();
        peerDto.setUploadCount(uploadCount);
        peerDto.setDownloadCount(downloadCount);
        peerDto.setCompleteCount(completeCount);
        torrentDTO.setPeer(peerDto);
        return torrentDTO;
    }

    public static List<TorrentDto> getTorrentDtoList(List<Torrent> torrentList, Map<Long, TorrentDiscount> discountMap,
                                                     Map<Long, Long> uploadMap,
                                                     Map<Long, Long> completeMap,
                                                     Map<Long, Long> downloadMap) {
        List<TorrentDto> torrentDtoList = new ArrayList<>();
        for (Torrent torrent : torrentList) {
            torrentDtoList.add(getTorrentDto(torrent, discountMap.get(torrent.getId()),
                    uploadMap.get(torrent.getId()),
                    completeMap.get(torrent.getId()),
                    downloadMap.get(torrent.getId())));
        }
        return torrentDtoList;
    }
}
