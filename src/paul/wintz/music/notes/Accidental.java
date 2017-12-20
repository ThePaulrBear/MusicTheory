package paul.wintz.music.notes;

import static com.google.common.base.Preconditions.checkArgument;

import paul.wintz.music.UnicodSymbol;

public enum Accidental {
	DOUBLE_FLAT (-2, UnicodSymbol.DOUBLE_FLAT),
	FLAT (-1, UnicodSymbol.FLAT),
	NATURAL (0, UnicodSymbol.NATURAL),
	SHARP (+1, UnicodSymbol.SHARP),
	DOUBLE_SHARP (+2, UnicodSymbol.DOUBLE_SHARP);

	final int shift;
	final String symbol;

	private Accidental(int shift, String symbol) {
		this.shift = shift;
		this.symbol = symbol;
	}

	private int getShift() {
		return shift;
	}

	String getSymbol() {
		return symbol;
	}

	static Accidental accidentalFromBaseAndPitchClass(NoteLetter noteLetter, PitchClass note) {
		int halfStepsBetween = PitchClass.getDifferneceInHalfSteps(noteLetter.getNoteNumber(), note.getNoteNumber());

		int accidentalShift = halfStepsBetween;

		String errorMsg = "\nNote: " + note.toString()
		+ "\nBase: " + noteLetter.toString()
		+ "\nHalf Steps Between: " + halfStepsBetween
		+ "\nAccidental Shift: " + accidentalShift
		;

		return Accidental.accidentalFromShift(accidentalShift);
	}

	/**
	 *
	 * @param shift
	 * 		Must be between -2 and +2.
	 * @return accidental
	 * 		Accidental that gives the shift specified in parameter.
	 *
	 */
	public static Accidental accidentalFromShift(int shift){
		checkArgument(-2 <= shift && shift <= 2, "Must be between -2 and +2.");
		shift %= 12;

		switch(shift){
		case -2:
		case 10:
			return DOUBLE_FLAT;
		case -1:
		case 11:
			return FLAT;
		case 0:
		case 12:
			return NATURAL;
		case +1:
		case -11:
			return SHARP;
		case +2:
		case -10:
			return DOUBLE_SHARP;
		default:
			throw new IllegalArgumentException("The shift, " + shift + ", was too far to be handled by an accidental");
		}
	}
}