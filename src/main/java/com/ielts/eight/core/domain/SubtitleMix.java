package com.ielts.eight.core.domain;

import java.util.Map;

/**
 * @author guangmiao@weierai.com
 * @date 2022/7/13 - 10:33 下午
 * @desc
 */
public class SubtitleMix {
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 字幕时间刻度
	 */
	private SubtitleTiming subtitleTiming;

	/**
	 * 台词
	 */
	private Map<String, SubtitleLineBase> subtitleLine;

	/**
	 * 语言类型
	 */
	private String languageType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SubtitleTiming getSubtitleTiming() {
		return subtitleTiming;
	}

	public void setSubtitleTiming(SubtitleTiming subtitleTiming) {
		this.subtitleTiming = subtitleTiming;
	}

	public Map<String, SubtitleLineBase> getSubtitleLine() {
		return subtitleLine;
	}

	public void setSubtitleLine(Map<String, SubtitleLineBase> subtitleLine) {
		this.subtitleLine = subtitleLine;
	}

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}
}
