package com.ielts.eight.core.domain;

import java.time.LocalTime;

/**
 * @author guangmiao
 * @date 2022/7/12 - 10:21 下午
 * @desc
 */
public class SubtitleTiming {
	private LocalTime start;
	private String startSuffix;

	private LocalTime end;
	private String endSuffix;

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}



	public boolean startTimeSame(LocalTime start){
		int hour = start.getHour();
		int hour1 = this.getStart().getHour();
		if(hour != hour1){
			return false;
		}

		int minute = start.getMinute();
		int minute1 = this.getStart().getMinute();
		if(minute != minute1){
			return false;
		}

		return Math.abs(this.start.getSecond() - start.getSecond()) <= 1;

	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("");
		sb.append("")
				.append(start);
		if(start.getSecond() == 0){
			sb.append(":00");
		}

		sb.append(startSuffix);
		sb.append(" --> ")
				.append(end);
		if(end.getSecond() == 0){
			sb.append(":00");
		}
		sb.append(endSuffix);
		return sb.toString();
	}

	public String getStartSuffix() {
		return startSuffix;
	}

	public void setStartSuffix(String startSuffix) {
		this.startSuffix = startSuffix;
	}

	public String getEndSuffix() {
		return endSuffix;
	}

	public void setEndSuffix(String endSuffix) {
		this.endSuffix = endSuffix;
	}
}
