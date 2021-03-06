package paul.wintz.music.chords;

import paul.wintz.music.*;
import paul.wintz.music.keys.Key;

public class AbsoluteRomanNumeralChord {
	private final RomanNumeral romanNumeral;
	private final Key key;
	private final AbsoluteChord chord;
	
	public AbsoluteRomanNumeralChord(RomanNumeral romanNumeral, Key key, AbsoluteChord chord) {
		this.romanNumeral = romanNumeral;
		this.key = key;
		this.chord = chord;
	}

	public RomanNumeral getRomanNumeral() {
		return romanNumeral;
	}

	public Key getKey() {
		return key;
	}

	public AbsoluteChord getChord() {
		return chord;
	}

	@Override
	public String toString(){
		return key.toString() + ": " + chord.toString() + " (" + romanNumeral.toString() + ")";
	}
}
