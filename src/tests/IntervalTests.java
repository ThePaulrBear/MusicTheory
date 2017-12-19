package tests;

import static org.junit.Assert.*;

import paul.wintz.music.*;
import paul.wintz.music.chords.*;
import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.music.notes.PitchClass;

import java.io.*;
import java.util.*;

import org.junit.*;


public class IntervalTests {

	@Test
	public void testIntevalQuality() throws Exception {
		
		IntervalEnum intervalEnum = IntervalEnum.MAJOR_NINTH;
		
		
	}
	
	@Test
	public void testGetDefaultInterval() throws Exception{
		for(IntervalEnum intervalEnum : IntervalEnum.values()){
			if(!intervalEnum.isDefault()) continue;
			assertEquals(intervalEnum, IntervalEnum.getDefaultInterval(0, intervalEnum.getHalfSteps()));
			assertEquals(intervalEnum, IntervalEnum.getDefaultInterval(0, -intervalEnum.getHalfSteps()));
		}
	}
	
	@Test 
	public void testGetThirdType(){
		ArrayList<PitchClass> notes = new ArrayList<>();
		notes.add(PitchClass.C);
		notes.add(PitchClass.E);
		IntervalEnum third = IntervalEnum.findThirdType(notes);
		assertEquals(IntervalEnum.MAJOR_THIRD, third);
	}
	
	@Test
	public void testChordQualities() throws Exception {
		for(ChordQuality quality : ChordQuality.values()){
			AbsoluteChord chord = new AbsoluteChord(PitchClass.C, quality);
			
			ArrayList<PitchClass> notes = chord.getNotes();
			IntervalEnum thirdType = IntervalEnum.findThirdType(notes);
			IntervalEnum fifthType = IntervalEnum.findFifthType(notes);
			IntervalEnum seventhType = IntervalEnum.findSeventhType(notes);
			IntervalEnum ninthType = IntervalEnum.findNinthType(notes);
			IntervalEnum eleventhType = IntervalEnum.findEleventhType(notes);
			IntervalEnum thirteenthType = IntervalEnum.findThirteenthType(notes);	
			
			String errorMsg = "\nQuality: " + quality.name() + "\nnotes: " + chord.pitchClassesToString();
			assertEquals(errorMsg, quality.getThird(), thirdType);
			assertEquals(errorMsg, quality.getFifth(), fifthType);
			assertEquals(errorMsg, quality.getSeventh(), seventhType);
			assertEquals(errorMsg, quality.getNinth(), ninthType);
			assertEquals(errorMsg, quality.getEleventh(), eleventhType);
			assertEquals(errorMsg, quality.getThirteenth(), thirteenthType);
		}
	}
}
