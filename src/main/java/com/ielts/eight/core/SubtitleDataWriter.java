package com.ielts.eight.core;

import com.ielts.eight.core.domain.Subtitle;
import com.ielts.eight.core.domain.SubtitleChineseLine;
import com.ielts.eight.core.domain.SubtitleEnglishLine;
import com.ielts.eight.core.domain.SubtitleLineBase;
import com.ielts.eight.core.domain.SubtitleMix;
import com.ielts.eight.core.domain.SubtitleTiming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guangmiao@weierai.com
 * @date 2022/7/13 - 10:27 下午
 * @desc
 */
public class SubtitleDataWriter {


	public static void main(String[] args) {
		SubtitleDataRead subtitleDataRead = new SubtitleDataRead();

		String englishPath = "/Users/sky/Desktop/ielts-eight/src/main/resources/The.Pursuit.of.Happyness.2006.1080p.720p.BluRay.x264.[YTS.MX]-English.txt";
		String chinesePath = "/Users/sky/Desktop/ielts-eight/src/main/resources/The Pursuit of Happyness-Chinese.txt";


		List<Subtitle> subtitleEnglish = subtitleDataRead.loadSubtitle(englishPath, "english");

		List<Subtitle> subtitleChinese = subtitleDataRead.loadSubtitle(chinesePath, "chinese");


		List<SubtitleMix> mix = mix(subtitleEnglish, subtitleChinese);
		write(mix);
	}

	/**
	 * 根据时间找相近的翻译
	 * @param englishSutitle
	 * @param chineseSubtitle
	 * @return
	 */
	public static List<SubtitleMix> mix(List<Subtitle> englishSutitle, List<Subtitle> chineseSubtitle){
		List<SubtitleMix> subtitleMixList = new ArrayList<>();

		// 已经匹配的字幕需要标记，下次匹配时过滤
		Map<Integer,Subtitle> englishAlreadySutitle = new HashMap();
		Map<Integer,Subtitle> chineseAlreadySutitle = new HashMap();


		for (Subtitle english : englishSutitle) {
			// 用于调试使用
			System.out.println();

			for (Subtitle chinese : chineseSubtitle) {

				SubtitleTiming englishSubtitleTiming = english.getSubtitleTiming();
				SubtitleTiming chineseSubtitleTiming = chinese.getSubtitleTiming();

				// 时间差在一秒以内
				boolean same = englishSubtitleTiming.startTimeSame(chineseSubtitleTiming.getStart());
				if(same){
					// 字幕已经匹配过
					if(englishAlreadySutitle.get(english.getId()) != null || chineseAlreadySutitle.get(chinese.getId()) != null){
						continue;
					}

					SubtitleMix subtitleMix = new SubtitleMix();
					subtitleMix.setId(english.getId());
					subtitleMix.setLanguageType("mix");
					subtitleMix.setSubtitleTiming(englishSubtitleTiming);


					Map<String, SubtitleLineBase> subtitleLine = new HashMap<>();
					subtitleLine.put("english", new SubtitleEnglishLine(english.getLines()));
					subtitleLine.put("chinese", new SubtitleChineseLine(chinese.getLines()));

					subtitleMix.setSubtitleLine(subtitleLine);

					subtitleMixList.add(subtitleMix);

					chineseAlreadySutitle.put(chinese.getId(),chinese);
					englishAlreadySutitle.put(english.getId(),english);
				}

			}
		}

		return subtitleMixList;
	}

	public static void write(List<SubtitleMix> mixes){
		Map<Integer, List<Object>> writeData = writerPre(mixes);

		try {
			File file = new File("src/main/resources/filename.srt");

			if (!file.exists()) {
				file.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			try {
				for (Map.Entry<Integer, List<Object>> entry : writeData.entrySet()) {
					List<Object> data = entry.getValue();
					try {
						for (Object item : data) {
							bw.write(item + "\r\n");
						}
						bw.write("\r\n");
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				bw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 预处理
	 * @param mixes
	 * @return
	 */
	private static Map<Integer, List<Object>> writerPre(List<SubtitleMix> mixes) {
		Map<Integer,List<Object>> writeData = new HashMap<>(2000);

		mixes.stream().forEach(item -> {
			Map<String, SubtitleLineBase> subtitleLine = item.getSubtitleLine();

			SubtitleLineBase enlish = subtitleLine.get("english");
			SubtitleLineBase chinese = subtitleLine.get("chinese");

			List<Object> data = new ArrayList<>();
			data.add(item.getId());
			data.add(item.getSubtitleTiming());

			if(enlish != null && enlish.getLines() != null) {
				data.add(enlish.getLines().toString());
			}

			if(chinese != null && chinese.getLines() != null) {
				data.add(chinese.getLines().toString());
			}

			writeData.put(item.getId(), data);
		});
		return writeData;
	}

}
