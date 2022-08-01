package com.ielts.eight.core;
import com.ielts.eight.core.domain.SubtitleEnglishLine;
import com.ielts.eight.core.domain.SubtitleLineBase;
import com.ielts.eight.core.domain.SubtitleTiming;
import java.util.HashMap;

import com.ielts.eight.core.domain.Subtitle;
import com.ielts.eight.core.domain.SubtitleMix;
import com.ielts.eight.core.domain.rule.StudySubtitleRule;
import com.ielts.eight.core.domain.rule.SubtitleRule;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author guangmiao@weierai.com
 * @date 2022/8/1 - 10:40 下午
 * @desc
 */
public class SingleDataWriter {
	public static void main(String[] args) {
		SubtitleDataRead subtitleDataRead = new SubtitleDataRead();

		String englishPath = "/Users/sky/hyh/idea/ielts-elight/src/main/resources/Flipped.2010.720p.BluRay.x264.[YTS.MX]-English的副本.srt";


		List<Subtitle> subtitleEnglish = subtitleDataRead.loadSubtitle(englishPath, "english");

		SubtitleRule subtitleRule = new StudySubtitleRule();


		subtitleEnglish = subtitleEnglish.stream()
				.filter(subtitle -> subtitle.checkLines(subtitleRule))
				.collect(Collectors.toList());

		List<SubtitleMix> mix = subtitleEnglish.stream().map(subtitle -> {
			SubtitleMix subtitleMix = new SubtitleMix();
			subtitleMix.setId(subtitle.getId());
			subtitleMix.setSubtitleTiming(subtitle.getSubtitleTiming());

			HashMap<String, SubtitleLineBase> subtitleLine = new HashMap<>();
			SubtitleLineBase english = new SubtitleEnglishLine(subtitle.getLines());
			subtitleLine.put("english", english);

			subtitleMix.setSubtitleLine(subtitleLine);
			subtitleMix.setLanguageType("english");

			return subtitleMix;
		}).collect(Collectors.toList());


		SubtitleDataWriter.write(mix);
	}
}
