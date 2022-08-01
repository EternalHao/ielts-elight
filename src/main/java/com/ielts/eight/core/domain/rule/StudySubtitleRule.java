package com.ielts.eight.core.domain.rule;

import com.ielts.eight.core.domain.SubtitleRegex;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author guangmiao@weierai.com
 * @date 2022/8/1 - 10:26 下午
 * @desc
 */
public class StudySubtitleRule implements SubtitleRule {

	@Override
	public boolean check(List<String> subtitleData) {
		Pattern p = Pattern.compile(SubtitleRegex.REGEX_CHINESE);

		List<String> studySubtitle = subtitleData.stream().filter(data -> {
			Matcher m = p.matcher(data);
			return m.find();
		}).collect(Collectors.toList());

		return !CollectionUtils.isEmpty(studySubtitle);
	}
}
