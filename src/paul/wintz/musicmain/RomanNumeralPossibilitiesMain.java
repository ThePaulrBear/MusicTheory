package paul.wintz.musicmain;
import static java.lang.System.out;

import java.util.List;

import paul.wintz.music.chords.*;
import paul.wintz.music.notes.PitchClass;

public class RomanNumeralPossibilitiesMain {
	//For a given chord, identify the keys that contain it, and the name of the chord in that key.
	//Also, show the name of the chord in each key and what chords would logically follow it.

	public static void main(String[] args) {
		AbsoluteChord chord = new AbsoluteChord(PitchClass.G_SHARP, ChordQuality.MAJOR_TRIAD);
		List<AbsoluteRomanNumeralChord> allPossible = chord.getAllPossibleRomanNumerals();
		for(AbsoluteRomanNumeralChord rn : allPossible){
			out.println(rn.getKey() + ": " + rn.getRomanNumeral());
		}
	}

}
