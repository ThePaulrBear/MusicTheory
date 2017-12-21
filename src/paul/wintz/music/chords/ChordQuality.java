package paul.wintz.music.chords;

import static paul.wintz.music.UnicodSymbol.*;
import static paul.wintz.music.intervals.IntervalEnum.*;

import java.util.*;

import com.google.common.collect.ImmutableSortedSet;

import paul.wintz.music.intervals.IntervalEnum;

public enum ChordQuality {
	// The order matter here, in that earlier entries will be preferred
	// when deciding
	SUS_2 				("sus2", PERFECT_FIFTH, MAJOR_NINTH),
	SUS_4 				("sus4", PERFECT_FIFTH, PERFECT_ELEVENTH),

	MINOR_TRIAD 		("min",  MINOR_THIRD, PERFECT_FIFTH),
	MINOR_7 			("min7", MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),
	MINOR_9 			("min9", MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MINOR_NINTH),
	MINOR_11			("min11", MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MAJOR_NINTH, PERFECT_ELEVENTH),
	MINOR_13			("min13", MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MAJOR_NINTH, PERFECT_ELEVENTH, MAJOR_THIRTEENTH),

	MAJOR_TRIAD 		("maj", MAJOR_THIRD, PERFECT_FIFTH),
	MAJOR_7 			("maj7", MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH),
	DOMINANT_7 			("7", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),
	MAJOR_ADD_9 		("add9", MAJOR_THIRD, PERFECT_FIFTH, MAJOR_NINTH),
	MAJOR_9 			("maj9", MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH, MAJOR_NINTH),
	DOMINANT_9 			("9", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MAJOR_NINTH),
	DOMINANT_MIN9 		("7b9", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MINOR_NINTH),
	DOMINANT_11			("11", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MAJOR_NINTH, PERFECT_ELEVENTH),
	DOMINANT_MAJ11		("maj11", MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH, MAJOR_NINTH, PERFECT_ELEVENTH),
	DOMINANT_13			("11", MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH, MAJOR_NINTH, PERFECT_ELEVENTH, MAJOR_THIRTEENTH),

	AUGMENTED		 	("+", MAJOR_THIRD, AUGMENTED_FIFTH),
	AUGMENTED_7 		("+7", MAJOR_THIRD, AUGMENTED_FIFTH, MINOR_SEVENTH),
	AUGMENTED_MAJ7		("+maj7", MAJOR_THIRD, AUGMENTED_FIFTH, MINOR_SEVENTH),

	DIMINISHED_TRIAD 	(DIMINISHED, MINOR_THIRD, TRITONE),
	DIMINISHED_7 		(DIMINISHED + "7", MINOR_THIRD, TRITONE, DIMINISHED_SEVENTH),
	HALF_DIMINISHED_7	(HALF_DIMINISHED + "7", MINOR_THIRD, TRITONE, MINOR_SEVENTH),
	DIMINISHED_MAJ7		(DIMINISHED + "maj7", MINOR_THIRD, TRITONE, MAJOR_SEVENTH),
	INVALID ("?");

	private final Set<IntervalEnum> intervalEnums;
	private final String sufix;

	//Seventh chords
	private ChordQuality(String sufix, IntervalEnum... intervals){
		this.sufix = sufix;
		this.intervalEnums = ImmutableSortedSet.copyOf(intervals); // Use a sorted set for optimization.
	}

	public Set<IntervalEnum> getIntervals(){
		return intervalEnums;
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

	public boolean hasSeventh(){
		return getSeventh().isPresent();
	}

	public boolean hasNinth(){
		return getSeventh().isPresent();
	}

	public boolean hasEleventh(){
		return getEleventh().isPresent();
	}

	public boolean hasThirteenth(){
		return getThirteenth().isPresent();
	}

	public String getSufix() {
		return sufix;
	}

	public boolean isValid(){
		return(this != INVALID);
	}


}
