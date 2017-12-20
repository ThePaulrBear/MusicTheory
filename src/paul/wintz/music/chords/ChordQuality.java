package paul.wintz.music.chords;

import static paul.wintz.music.UnicodSymbol.*;
import static paul.wintz.music.intervals.IntervalEnum.*;

import java.util.*;

import com.google.common.collect.ImmutableSet;

import paul.wintz.music.intervals.IntervalEnum;

public enum ChordQuality {
	// The order matter here, in that earlier entries will be preferred
	// when deciding
	OPEN_FIFTH ("~", PERFECT_FIFTH),
	SUS_2 ("sus2", PERFECT_FIFTH, MAJOR_NINTH),
	SUS_4 ("sus4", PERFECT_FIFTH, PERFECT_ELEVENTH),

	MINOR_TRIAD 		("min", MINOR_THIRD, PERFECT_FIFTH),
	MINOR_7 			("min7", MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),

	MAJOR_TRIAD 		("maj", MAJOR_THIRD, PERFECT_FIFTH),
	MAJOR_7 			("maj7", MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH),
	DOMINANT_7 			("7", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),

	AUGMENTED_TRIAD 	("+", MAJOR_THIRD, AUGMENTED_FIFTH),

	DIMINISHED_TRIAD 	(DIMINISHED, MINOR_THIRD, TRITONE),
	DIMINISHED_7 		(DIMINISHED + "7", MINOR_THIRD, TRITONE, DIMINISHED_SEVENTH),
	HALF_DIMINISHED_7	(HALF_DIMINISHED + "7", MINOR_THIRD, TRITONE, MINOR_SEVENTH),
	INVALID ("?");

	private final Set<IntervalEnum> intervalEnums;
	private final String sufix;

	//Seventh chords
	private ChordQuality(String sufix, IntervalEnum... intervals){
		this.sufix = sufix;
		this.intervalEnums = ImmutableSet.copyOf(intervals);
	}

	public Set<IntervalEnum> getIntervals(){
		return intervalEnums;
	}

	public static ChordQuality calculate(IntervalEnum... intervals){
		return IntervalsToChordQualityMapper.chordQualityFromIntervalSet(intervals);
	}

	boolean isTriad(){
		return getThird().isPresent() && getFifth().isPresent();
	}

	public boolean isSeventhChord(){
		return getSeventh().isPresent();
	}

	public Optional<IntervalEnum> getThird() {
		return intervalFromScaleDegree(3);
	}

	public Optional<IntervalEnum> getFifth() {
		return intervalFromScaleDegree(5);
	}

	public Optional<IntervalEnum> getSeventh() {
		return intervalFromScaleDegree(7);
	}

	public Optional<IntervalEnum> getNinth() {
		return intervalFromScaleDegree(9);
	}

	public Optional<IntervalEnum> getEleventh() {
		return intervalFromScaleDegree(11);
	}

	public Optional<IntervalEnum> getThirteenth() {
		return intervalFromScaleDegree(13);
	}

	private Optional<IntervalEnum> intervalFromScaleDegree(int scaleDegree) {
		return getIntervals().stream()
				.filter(interval -> interval.getScaleSteps() == scaleDegree).findFirst();
	}


	public String getSufix() {
		return sufix;
	}

	public boolean isValid(){
		return(this != INVALID);
	}


}
