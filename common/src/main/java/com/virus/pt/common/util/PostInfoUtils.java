package com.virus.pt.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.virus.pt.common.enums.PostInfoTypeEnum;
import com.virus.pt.model.dataobject.PostInfo;
import com.virus.pt.model.dto.PostInfoDto;
import com.virus.pt.model.vo.PostInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 8:42 下午
 * @email zzy.main@gmail.com
 */
public class PostInfoUtils {
    private static final Logger logger = LoggerFactory.getLogger(PostInfoUtils.class);

    private static final String IMDB_API_URL = "http://www.omdbapi.com";

    private static final String DOUBAN_API_URL = "https://api.douban.com/v2/";
    private static final String DOUBAN_MOVIE_TYPE = "movie";
    private static final String DOUBAN_MUSIC_TYPE = "music";
    private static final String DOUBAN_BOOK_TYPE = "book";
    private static final String DOUBAN_API_KEY = "0df993c66c0c636e29ecbb5344252a4a";

    private static WebClient imdbWebClient = WebClient.builder()
            .baseUrl(IMDB_API_URL)
            .build();

    private static WebClient doubanWebClient = WebClient.builder()
            .baseUrl(DOUBAN_API_URL)
            .build();

    public static Mono<JsonNode> get(PostInfoVo postInfoVo) {
        if (postInfoVo.getType() == PostInfoTypeEnum.IMDB.getInfoType()) {
            return imdbWebClient.get()
                    .uri("/?i=tt{imdbId}&apikey={key}", postInfoVo.getId(), VirusUtils.config.getImdbKey())
                    .retrieve()
                    .bodyToMono(JsonNode.class);
        } else {
            String type = DOUBAN_MOVIE_TYPE;
            if (postInfoVo.getType() == PostInfoTypeEnum.DOUBAN_MUSIC.getInfoType()) {
                type = DOUBAN_MUSIC_TYPE;
            } else if (postInfoVo.getType() == PostInfoTypeEnum.DOUBAN_BOOK.getInfoType()) {
                type = DOUBAN_BOOK_TYPE;
            }
            return doubanWebClient.get()
                    .uri("{type}/{id}?apikey={key}", type, postInfoVo.getId(), DOUBAN_API_KEY)
                    .retrieve()
                    .bodyToMono(JsonNode.class);
        }
    }

    public static PostInfoDto getPostInfoDto(PostInfo postInfo) throws IOException {
        return getPostInfoDto(postInfo.getInfoType(), postInfo.getUkInfoId(), postInfo.getContent());
    }

    public static PostInfoDto getPostInfoDto(short type, long id, String content) throws IOException {
        return getPostInfoDto(type, id, JackJsonUtils.parse(content));
    }

    public static PostInfoDto getPostInfoDto(short type, long id, JsonNode rootNode) {
        PostInfoDto postInfoDto = new PostInfoDto();
        postInfoDto.setId(id);
        if (type == PostInfoTypeEnum.IMDB.getInfoType()) {
            JsonNode titleNode = rootNode.get("Title");
            if (titleNode != null) {
                postInfoDto.setTitle(titleNode.asText());
            }
            JsonNode averageNode = rootNode.get("imdbRating");
            if (averageNode != null) {
                postInfoDto.setRatingAverage(Float.valueOf(averageNode.asText()));
            }
            JsonNode numRatersNode = rootNode.get("imdbVotes");
            if (numRatersNode != null) {
                postInfoDto.setRatingNumRaters(Integer.valueOf(numRatersNode.asText().replaceAll(",", "")));
            }
            JsonNode yearNode = rootNode.get("Year");
            if (yearNode != null) {
                postInfoDto.setYear(yearNode.asText());
            }
            JsonNode languageNode = rootNode.get("Language");
            if (languageNode != null) {
                postInfoDto.setLanguage(languageNode.asText());
            }
            JsonNode countryNode = rootNode.get("Country");
            if (countryNode != null) {
                postInfoDto.setCountry(countryNode.asText());
            }
            JsonNode plotNode = rootNode.get("Plot");
            if (plotNode != null) {
                postInfoDto.setSummary(plotNode.asText());
            }
            JsonNode posterNode = rootNode.get("Poster");
            if (posterNode != null) {
                postInfoDto.setPoster(posterNode.asText());
            }
            JsonNode writerNode = rootNode.get("Writer");
            if (posterNode != null) {
                postInfoDto.setWriter(writerNode.asText());
            }
            JsonNode actorsNode = rootNode.get("Actors");
            if (actorsNode != null) {
                postInfoDto.setActors(actorsNode.asText());
            }
            JsonNode releasedNode = rootNode.get("Released");
            if (releasedNode != null) {
                postInfoDto.setReleased(releasedNode.asText());
            }
            JsonNode runtimeNode = rootNode.get("Runtime");
            if (runtimeNode != null) {
                postInfoDto.setRuntime(runtimeNode.asText());
            }
        } else {
            // get title
            JsonNode titleNode = rootNode.get("title");
            if (titleNode != null && StringUtils.isNotBlank(titleNode.asText())) {
                postInfoDto.setTitle(titleNode.asText());
            }
            JsonNode ratingNode = rootNode.get("rating");
            if (ratingNode != null) {
                JsonNode averageNode = ratingNode.get("average");
                if (averageNode != null && StringUtils.isNotBlank(averageNode.asText())) {
                    postInfoDto.setRatingAverage(Float.valueOf(averageNode.asText()));
                } else {
                    postInfoDto.setRatingAverage(0F);
                }
                JsonNode numRatersNode = ratingNode.get("numRaters");
                if (numRatersNode != null) {
                    postInfoDto.setRatingNumRaters(numRatersNode.asInt());
                } else {
                    postInfoDto.setRatingNumRaters(0);
                }
            }
            JsonNode attrsNode = rootNode.get("attrs");
            if (attrsNode != null) {
                postInfoDto.setYear(getArrayNameString(attrsNode, "year"));
                postInfoDto.setLanguage(getArrayNameString(attrsNode, "language"));
                postInfoDto.setCountry(getArrayNameString(attrsNode, "country"));
                // get writer
                postInfoDto.setWriter(getArrayNameString(attrsNode, "writer"));
                // get cast
                postInfoDto.setActors(getArrayNameString(attrsNode, "cast"));
                postInfoDto.setReleased(getArrayNameString(attrsNode, "pubdate"));
                postInfoDto.setRuntime(getArrayNameString(attrsNode, "movie_duration"));
            }
            // get summary
            JsonNode summaryNode = rootNode.get("summary");
            if (summaryNode != null && StringUtils.isNotBlank(summaryNode.asText())) {
                postInfoDto.setSummary(summaryNode.asText());
            }
            // get poster
            JsonNode imageNode = rootNode.get("image");
            JsonNode imagesNode = rootNode.get("images");
            if (imageNode != null && StringUtils.isNotBlank(imageNode.asText())) {
                postInfoDto.setPoster(imageNode.asText());
            } else if (imagesNode != null && StringUtils.isNotBlank(imagesNode.get(0).asText())) {
                postInfoDto.setPoster(imagesNode.get(0).asText());
            }
        }
        return postInfoDto;
    }

    private static String getArrayNameString(JsonNode node, String arrayName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (node != null) {
            JsonNode arrayNode = node.get(arrayName);
            if (arrayNode != null) {
                for (int i = 0; i < arrayNode.size(); i++) {
                    stringBuilder.append(arrayNode.get(i).asText());
                    if (i != arrayNode.size() - 1) {
                        stringBuilder.append(" / ");
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
