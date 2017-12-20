package paul.wintz.music.intervals;

import static java.lang.Math.abs;

import java.util.List;

import com.google.common.base.Preconditions;

import paul.wintz.music.notes.PitchClass;

//Intervals will default to the first interval with a given scaleSteps of half-steps.
//Enharmonic intervals are typed in the same line
public enum IntervalEnum {
	UNISON 			(0, 0),
	MINOR_SECOND 	(1, 1),
	MAJOR_SECOND 	(1, 2),
	MINOR_THIRD 	(2, 3),
	MAJOR_THIRD 	(2, 4),
	PERFECT_FOURTH 	(3, 5),
	TRITONE			(4, 6),	AUGMENTED_FOURTH(4, 6, false),
	PERFECT_FIFTH 	(4, 7),
	MINOR_SIXTH 	(5, 8),	AUGMENTED_FIFTH (5, 8, false),
	MAJOR_SIXTH 	(5, 9),	DIMINISHED_SEVENTH(7,9, false),
	MINOR_SEVENTH 	(6, 10),
	MAJOR_SEVENTH 	(6, 11),
	OCTAVE 			(7, 12),
	MINOR_NINTH 	(8, 13),
	MAJOR_NINTH		(8, 14),
	MINOR_TENTH		(9, 15),
	MAJOR_TENTH		(9, 16),
	PERFECT_ELEVENTH(10, 17),
	OCTAVE_PLUS_TRITONE(10, 18), //Augmented eleventh
	PERFECT_TWELFTH	(11, 19),
	MINOR_THIRTEENTH(12, 20),
	MAJOR_THIRTEENTH(12, 21),
	MINOR_FOURTEENTH(13, 22),
	MAJOR_FOURTEENTH(13, 23),
	TWO_OCTAVES		(14, 24),
	NULL			(99, 99),
	MULTIPLE		(100, 100);

	public static final int MAX_SCALE_DEGREE = 14;
	public static final int MAX_HALF_STEPS = 24;
	private static final int HALF_STEPS_IN_OCTAVE = 12;
	private final int halfSteps;
	private final int scaleSteps;
	private final boolean isDefault;

	private IntervalEnum(int scaleSteps, int halfSteps){
		this.scaleSteps = scaleSteps;
		this.halfSteps = halfSteps;
		this.isDefault = true;
	}

	private IntervalEnum(int scaleSteps, int halfSteps, boolean isDefault){
		this.scaleSteps = scaleSteps;
		this.halfSteps = halfSteps;
		this.isDefault = isDefault;
	}

	public int getScaleSteps(){
		return this.scaleSteps;
	}

	public int getHalfSteps(){
		return halfSteps;
	}

	public boolean isDefault() {
		return isDefault;
	}

	private static int getScaleSteps(int noteNumber1, int noteNumber2){
		IntervalEnum deg = getDefaultInterval(noteNumber1, noteNumber2);
		switch(deg){
		case UNISON:
			return 1;
		case MINOR_SECOND:
		case MAJOR_SECOND:
			return 2;
		case MINOR_THIRD:
		case MAJOR_THIRD:
			return 3;
		case PERFECT_FOURTH:
			return 4;
		case TRITONE:
		case PERFECT_FIFTH:
			return 5;
		case MINOR_SIXTH:
		case MAJOR_SIXTH:
			return 6;
		case MINOR_SEVENTH:
		case MAJOR_SEVENTH:
			return 7;
		case OCTAVE:
			return 8;
		case MINOR_NINTH:
		case MAJOR_NINTH:
			return 9;
		case MINOR_TENTH:
		case MAJOR_TENTH:
			return 10;
		case PERFECT_ELEVENTH:
			return 11;
		case OCTAVE_PLUS_TRITONE:
		case PERFECT_TWELFTH:
			return 12;
		case MINOR_THIRTEENTH:
		case MAJOR_THIRTEENTH:
			return 13;
		case MINOR_FOURTEENTH:
		case MAJOR_FOURTEENTH:
			return 14;
		case TWO_OCTAVES:
			return 15;
		default:
			throw new IllegalArgumentException("Error: degree is too large: " + deg);
		}
	}

	public static IntervalEnum getDefaultInterval(int noteNumber1, int noteNumber2) {
		int halfSteps = abs(noteNumber1 - noteNumber2);

		for(IntervalEnum canidate : IntervalEnum.values()){
			if(canidate.getHalfSteps() == halfSteps) return canidate;
		}

		throw new IllegalArgumentException("The interval from " + noteNumber2 + " to " + noteNumber2 + " was not foudn");
	}

	public static IntervalEnum findOddIntervalUp(PitchClass lowerNote, PitchClass upperNote){
		int low = lowerNote.getNoteNumber();
		int up = upperNote.getNoteNumber();

		while(low > up || IntervalEnum.isIntervalEven(low, up)){
			up += HALF_STEPS_IN_OCTAVE;
		}

		return getDefaultInterval(low, up);
	}

	public static IntervalEnum findIntervalType(List<PitchClass> notes, int scaleSteps){
		IntervalEnum intervalEnum = IntervalEnum.NULL;
		PitchClass lowerNote = notes.get(0);
		for(PitchClass upperNote : notes){
			IntervalEnum possibleInterval = IntervalEnum.findOddIntervalUp(lowerNote, upperNote);
			if(possibleInterval.getScaleSteps() < scaleSteps) {
				continue;
			} else if(possibleInterval.getScaleSteps() > scaleSteps) {
				break;
			}

			if (intervalEnum == null) {
				intervalEnum = possibleInterval;
			} else
				return IntervalEnum.MULTIPLE;
		}
		return intervalEnum;
	}

	private static boolean isIntervalEven(int lowDegree, int upDegree){
		return getScaleSteps(lowDegree, upDegree) % 2 == 0;
	}

	public enum Direction {
		UP, DOWN
	}

	/**
	 * Looks through an array of notes and what quality the interval
	 * @param notes
	 * @param scaleSteps
	 * @return the quality of the interval of the given scaleSteps (e.g. third) from the bottom note to any notes in the array.
	 * If there are notes at the same interval scaleSteps but different qualities, then "IntervalEnum.MULTIPLE" is returned.
	 * If none are found, then "Intrval.Null" is returned.
	 */
	public static IntervalEnum findQualityOfOridinal(List<PitchClass> notes, int scaleSteps){
		IntervalEnum intervalEnum = IntervalEnum.NULL;

		PitchClass root = notes.get(0);
		for(int i = 1; i < notes.size(); i++){
			IntervalEnum canidate = IntervalEnum.findOddIntervalUp(root, notes.get(i));

			if(canidate.getScaleSteps() < scaleSteps){
				continue;
			}
			if(canidate.getScaleSteps() > scaleSteps){
				break;
			}
			if (intervalEnum == IntervalEnum.NULL){
				intervalEnum = canidate;
			} else
				return IntervalEnum.MULTIPLE;
		}

		Preconditions.checkState(intervalEnum.getScaleSteps() == scaleSteps || intervalEnum == IntervalEnum.NULL);
		return intervalEnum;
	}



	public static IntervalEnum findThirdType(List<PitchClass> notes){
		return findQualityOfOridinal(notes, 2);
	}
	/** DOES NOT HANDLE AUGMENTED FIFTHS
	 * @return
	 */
	public static IntervalEnum findFifthType(List<PitchClass> notes) {
		return findQualityOfOridinal(notes, 4);
	}
	public static IntervalEnum findSeventhType(List<PitchClass> notes) {

		return findQualityOfOridinal(notes, 6);
	}

	public static IntervalEnum findNinthType(List<PitchClass> notes) {
		return findQualityOfOridinal(notes, 8);

	}
	public static IntervalEnum findEleventhType(List<PitchClass> notes) {
		return findQualityOfOridinal(notes, 10);
	}

	public static IntervalEnum findThirteenthType(List<PitchClass> notes) {
		return findQualityOfOridinal(notes, 12);
	}

	public boolean isPresent() {
		return this != NULL;
	}
}
