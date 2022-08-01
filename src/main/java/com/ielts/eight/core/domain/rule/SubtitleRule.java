package com.ielts.eight.core.domain.rule;

import java.util.List;

/**
 * @author guangmiao@weierai.com
 * @date 2022/8/1 - 10:24 下午
 * @desc
 */
public interface SubtitleRule {
	/**
	 * 台词校验规则
	 * @param subtitleData
	 * @return
	 */
	boolean check(List<String> subtitleData);
}
