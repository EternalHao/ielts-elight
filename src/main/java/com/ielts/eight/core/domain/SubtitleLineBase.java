package com.ielts.eight.core.domain;

import java.util.List;

/**
 * @author guangmiao@weierai.com
 * @date 2022/7/13 - 10:35 下午
 * @desc
 */
public abstract class SubtitleLineBase {
	/**
	 * 台词
	 */
	private List<String> lines;

	public SubtitleLineBase(List<String> lines) {
		this.lines = lines;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}
}
