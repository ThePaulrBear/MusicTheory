package paul.wintz.music.keys;

import static com.google.common.base.Preconditions.checkArgument;
import static paul.wintz.music.intervals.IntervalEnum.MAJOR_THIRD;

import java.util.Optional;

import paul.wintz.music.chords.ChordQuality;
import paul.wintz.music.intervals.IntervalEnum;

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

		String romanNumeral = this.romanNumeral;

		Optional<IntervalEnum> third = quality.getThird();
		if (third.isPresent() && third.get() == MAJOR_THIRD) {
			romanNumeral = romanNumeral.toUpperCase();
		} else {
			romanNumeral = romanNumeral.toLowerCase();
		}

		romanNumeral += quality.getSufix();

		return romanNumeral;
	}
}
