package com.ielts.eight.core;

import com.ielts.eight.core.domain.Subtitle;
import com.ielts.eight.core.domain.SubtitleTiming;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guangmiao@weierai.com
 * @date 2022/7/12 - 10:26 下午
 * @desc
 */
public class SubtitleDataRead {


//	public static void main(String[] args) {
//		String filePath = "/Users/sky/Desktop/ielts-eight/src/main/resources/Forrest.Gump.1994.1080p.BrRip.x264.YIFY.srt";
//		List<Subtitle> subtitleList = loadSubtitle(filePath,"english");
//		System.out.println(subtitleList);
//	}

	public List<Subtitle> loadSubtitle(String filePath, String languageType) {
			List<List<String>> segment = segment(filePath);

			List<Subtitle> subtitleList = new ArrayList<>();

			for (List<String> list : segment) {
				Subtitle subtitle = new Subtitle();
				subtitle.setLanguageType(languageType);
				List<String> lines = new ArrayList<>();

				list.stream().forEach(line -> {
					line = specialUnicode(line);
					if (subtitle.checkId(line)) {
						subtitle.setId(Integer.valueOf(line));
					}else if (subtitle.checkSubtitleTiming(line)) {
						SubtitleTiming subtitleTiming = new SubtitleTiming();
						String[] split = line.split("-->");


						String startStr = split[0].split("[,]")[0].trim();
						String startSuffix = "," + split[0].split("[,]")[1].trim();
						LocalTime startTime = LocalTime.parse(specialUnicode(startStr), DateTimeFormatter.ISO_TIME);
						subtitleTiming.setStart(startTime);
						subtitleTiming.setStartSuffix(startSuffix);

						String endStr = split[1].split("[,]")[0].trim();
						String endSuffix = "," + split[1].split("[,]")[1].trim();
						LocalTime endTime = LocalTime.parse(specialUnicode(endStr), DateTimeFormatter.ISO_TIME);
						subtitleTiming.setEnd(endTime);
						subtitleTiming.setEndSuffix(endSuffix);

						subtitle.setSubtitleTiming(subtitleTiming);
					}else if (subtitle.checkLine(line)) {
						lines.add(line);
					}else if(line.isEmpty()) {
						subtitle.setLines(lines);
						subtitleList.add(subtitle);
					}
				});
			}

			return subtitleList;
		}

	/**
	 * 将文本行分段聚合
	 * @param file
	 * @return
	 */
	public List<List<String>> segment(String file) {
		List<List<String>> box = new ArrayList<>();
		String line;

		try {
			LineNumberReader reader = new LineNumberReader(new FileReader(file));
			List<String> lines = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				lines.add(line);

				if(line.isEmpty()){
					box.add(lines);
					lines = new ArrayList<>();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return box;
	}

	/**
	 * 处理奇怪的字符
	 * @param str
	 * @return
	 */
	public String specialUnicode(String str){
		if (str.startsWith("\uFEFF")){
			str = str.replace("\uFEFF", "");
		}else if (str.endsWith("\uFEFF")){
			str = str.replace("\uFEFF","");
		}
		return str;
	}
}
