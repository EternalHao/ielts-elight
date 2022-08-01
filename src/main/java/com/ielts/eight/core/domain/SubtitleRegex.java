package com.ielts.eight.core.domain;

/**
 * @author guangmiao@weierai.com
 * @date 2022/8/1 - 10:27 下午
 * @desc
 */
public class SubtitleRegex {
	/**
	 * 校验字符
	 */
	public static final String REGEX_USERNAME = "^[A-Za-z]+";

	/**
	 * 校验是否含有汉子
	 */
	public static final String REGEX_CHINESE = ".*[\\u4E00-\\u9FA5]{1,4}.*";

}
