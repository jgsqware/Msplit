package com.example.zsplit;

import java.util.ArrayList;
import java.util.List;

import com.example.zsplit.urnmodel.Urn;
import com.example.zsplit.urnmodel.UrnSplit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplitRow {
	private UrnSplit urnSplit;
	private RunSplit runSplit;
	
	public SplitRow() {
	}
	
	public SplitRow(UrnSplit urnSplit, RunSplit runSplit) {
		super();
		this.urnSplit = urnSplit;
		this.runSplit = runSplit;
	}

	public UrnSplit getUrnSplit() {
		return urnSplit;
	}

	public void setUrnSplit(UrnSplit urnSplit) {
		this.urnSplit = urnSplit;
	}

	public RunSplit getRunSplit() {
		return runSplit;
	}

	public void setRunSplit(RunSplit runSplit) {
		this.runSplit = runSplit;
	}

	public void reset() {
		runSplit = null;
	}
	
	public static List<SplitRow> createSplitRows(Urn urn){
		List<SplitRow> rows = new ArrayList<SplitRow>();
		for(UrnSplit s: urn.getSplits()){
			rows.add(new SplitRow(s, null));
		}
		return rows;
	}

}
