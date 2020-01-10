package com.virus.pt.pestilence.controller;

import com.virus.pt.common.exception.TipException;
import org.apache.commons.codec.DecoderException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: intent
 * @Date: 19-11-2 下午2:10
 */
public interface Announce {
    String retScrape(HttpServletRequest request) throws DecoderException;

    String retAnnounce(HttpServletRequest request) throws DecoderException, TipException;
}
