package com.ielts.eight.core.domain;

import com.ielts.eight.core.domain.rule.SubtitleRule;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guangmiao
 * @date 2022/7/12 - 10:18 下午
 * @desc
 */
public class Subtitle {
	public static final String REGEX_USERNAME = "^[A-Za-z]+";

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
	private List<String> lines;

	/**
	 * 语言类型
	 */
	private String languageType;

	public boolean checkId(String line){
		try{
			Long.valueOf(line);
		}catch (Exception e){
			return false;
		}

		return true;
	}

	public boolean checkSubtitleTiming(String line){
		return line.contains("-->");
	}

	public boolean checkLines(SubtitleRule subtitleRule){
		return subtitleRule.check(this.lines);
	}

	public boolean checkLine(String content){
		if(content.isEmpty()){
			return false;
		}

		if(this.languageType.equals("english")){
			Pattern p = Pattern.compile(REGEX_USERNAME);
			Matcher m = p.matcher(content);

			boolean b = m.find();
			if(b == true){
				return b;
			}

			p = Pattern.compile("^[(/-]+");
			m = p.matcher(content);


			return m.find();
		}
		return true;
	}

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

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}
}
