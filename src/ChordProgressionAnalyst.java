import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Random;

import music.AbsoluteChord;
import music.AbsoluteRomanNumeralChord;
import music.ChordQuality;
import music.PitchClass;

public class ChordProgressionAnalyst {
	static Random random = new Random();
	//For a given chord, identify the keys that contain it, and the name of the chord in that key.
	//Also, show the name of the chord in each key and what chords would logically follow it. 
	
	public static void main(String[] args) {
//		AbsoluteChord.test();
		AbsoluteChord chord = new AbsoluteChord(PitchClass.Ab, ChordQuality.MAJOR_TRIAD);
		ArrayList<AbsoluteRomanNumeralChord> allPossible = chord.getAllPossibleRomanNumerals();
		for(AbsoluteRomanNumeralChord rn : allPossible){
			out.println(rn.getKey().toString() + ": " + rn.getRomanNumeral().toString());
		}
		
		
//		ChordProgressionAnalyst.test();	
	}

	public static void test(){			
	//		for (ChordQuality qual : ChordQuality.values()) {
	//			AbsoluteChord chordToMatch = new AbsoluteChord(PitchClass.C, qual);//= chordRelative.calculateChord(PitchClass.C);
	//			
	//			System.out.print("\n" + chordToMatch.toString() + "\n");
	//			for (PitchClass tonicOfKey : PitchClass.values()) {
	//				
	//				for (Mode mode : values()) {
	//					ArrayList<RomanNumeral> matchingChords = mode.getMatchingRelativeChords(tonicOfKey,
	//							chordToMatch);
	//					if (matchingChords.isEmpty())
	//						continue;
	//					System.out.print("\t" + tonicOfKey.name() + " " + mode.toString() + ": ");
	//					for (RomanNumeral matchingChord : matchingChords) {
	//						System.out.print(matchingChord.toString());
	//					}
	//					System.out.println();
	//				}
	//			} 
	//		}
		
			audio.Main.produceProgression();
		}

	
}
