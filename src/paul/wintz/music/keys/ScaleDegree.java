package paul.wintz.music.keys;

import static com.google.common.base.Preconditions.checkArgument;
import static paul.wintz.music.intervals.IntervalEnum.MAJOR_THIRD;

import paul.wintz.music.chords.ChordQuality;

public enum ScaleDegree {
	TONIC ("I"),
	SUPERTONIC ("II"),
	MEDIANT ("III"),
	SUBDOMINANT ("IV"),
	DOMINANT ("V"),
	SUBMEDIANT ("VI"),
	LEADING_TONE ("VII");

	enum Modification {
		LOWERED, NONE, RAISED;
	}

	final String romanNumeral;

	ScaleDegree(String romanNumeral){
		this.romanNumeral = romanNumeral;
	}

	int getNumber(){
		return this.ordinal();
	}

	/**
	 * Returns the Roman numeral of the scale degree. Capitalized if major or augmented,
	 * lower-case if minor or diminished.
	 */
	public String getRomanNumeral(ChordQuality quality) {
		checkArgument(quality.isValid(), "Invalid Chord Quality");

		String s = this.romanNumeral;

		if (quality.getThird() == MAJOR_THIRD) {
			s = s.toUpperCase();
		} else {
			s = s.toLowerCase();
		}

		s += quality.getSufix();

		return s;
	}
}
