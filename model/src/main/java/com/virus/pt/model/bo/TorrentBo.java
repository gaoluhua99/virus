package com.virus.pt.model.bo;


import com.virus.pt.model.dataobject.Torrent;

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

//    public static TorrentDto getTorrentDto(Torrent torrent, Discount discount,
//                                           Integer uploadCount, Integer completeCount, Integer downloadCount) {
//        TorrentDto torrentDTO = new TorrentDto();
//        BeanUtils.copyFieldToBean(torrent, torrentDTO);
//        torrentDTO.setHash(Hex.encodeHexString(torrent.getInfoHash()));
//        if (discount != null) {
//            torrentDTO.setDiscount(DiscountBo.getDiscountDto(discount));
//        }
//        torrentDTO.setUploadCount(uploadCount);
//        torrentDTO.setDownloadCount(downloadCount);
//        torrentDTO.setCompleteCount(completeCount);
//        return torrentDTO;
//    }
//
//    public static List<TorrentDto> getTorrentDtoList(List<Torrent> torrentList, Map<Integer, Discount> discountMap,
//                                                     Map<Integer, Integer> uploadMap,
//                                                     Map<Integer, Integer> completeMap,
//                                                     Map<Integer, Integer> downloadMap) {
//        List<TorrentDto> torrentDtoList = new ArrayList<>();
//        for (Torrent torrent : torrentList) {
//            torrentDtoList.add(getTorrentDto(torrent, discountMap.get(torrent.getId()),
//                    uploadMap.get(torrent.getId()),
//                    completeMap.get(torrent.getId()),
//                    downloadMap.get(torrent.getId())));
//        }
//        return torrentDtoList;
//    }
}
