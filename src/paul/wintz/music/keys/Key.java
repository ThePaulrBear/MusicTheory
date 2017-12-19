package paul.wintz.music.keys;

import java.util.*;

import paul.wintz.music.*;
import paul.wintz.music.chords.*;
import paul.wintz.music.notes.PitchClass;

public class Key  {

	private static final Map<String, Key> allKeys = new HashMap<>();
	private static final List<Key> allKeysArray = new ArrayList<>();

	static {
		for(PitchClass tonic : PitchClass.values()){
			for(Mode mode : Mode.values()){
				Key key = new Key(tonic, mode);
				allKeys.put(tonic.name() + mode.name(), key);
				allKeysArray.add(key);
			}
		}
	}

	private PitchClass tonic;
	private Mode mode;

	private Key(PitchClass tonic, Mode mode) {
		this.tonic = tonic;
		this.mode = mode;
	}

	public AbsoluteChord absoluteChordFromRomanNumeral(RomanNumeralChord romanNumeral){
		PitchClass root = this.getTonic().addHalfsteps(romanNumeral.getHalfStepsAboveTonic());

		return new AbsoluteChord(root, romanNumeral.quality);
	}

	/**
	 *
	 * @param chord
	 * @return
	 * Returns null if no Roman numeral is found
	 */
	public RomanNumeralChord romanNumeralFromAbsoluteChord(AbsoluteChord chord){
		int halfStepsAboveTonic = PitchClass.getDifferneceInHalfSteps(tonic, chord.getRoot());
		if(halfStepsAboveTonic < 0) {
			halfStepsAboveTonic += 12;
		}
		return RomanNumeralChord.makeRomanNumeral(this.mode, halfStepsAboveTonic, chord.quality);
	}

	public boolean containsChord(AbsoluteChord chord){
		return (romanNumeralFromAbsoluteChord(chord) != null);
	}

	public static Key getKey(PitchClass tonic, Mode mode){
		return allKeys.get(tonic.name() + mode.name());
	}
	public static List<Key> getAllKeys(){
		return allKeysArray;
	}

	public PitchClass getTonic() {
		return tonic;
	}

	public void setTonic(PitchClass tonic) {
		this.tonic = tonic;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public String toString(){
		return this.tonic.name() + " " + this.mode.name();
	}
}
