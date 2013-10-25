package com.msplit.urnmodel;

import java.io.Serializable;

public class UrnSplit implements Comparable<UrnSplit>, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int time;
	private int bestSegment;
	transient private Urn urn;
	transient private int index;

	public UrnSplit(String title, int time) {
		this.name = title;
		this.time = time;
	}

	public UrnSplit(UrnSplit urnSplit) {
		this.name = urnSplit.getName();
		this.time = urnSplit.getTime();
		this.bestSegment = urnSplit.getTime();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public int getSegmentTime(){
		if(index == 0){
			return time;
		} else {
			return urn.getSplits().get(index-1).getTime() - time;
		}
	}

	public int getBestSegment() {
		return bestSegment;
	}

	public void setBestSegment(int bestSegment) {
		this.bestSegment = bestSegment;
	}

	public Urn getUrn() {
		return urn;
	}

	public void setUrn(Urn urn) {
		this.urn = urn;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int compareTo(UrnSplit another) {
		return this.time - another.time;
	}

}
