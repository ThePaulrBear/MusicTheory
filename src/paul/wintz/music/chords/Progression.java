package paul.wintz.music.chords;

import static java.lang.System.out;

import java.util.*;

import paul.wintz.music.keys.Key;
import paul.wintz.music.notes.PitchClass;

public class Progression {

	private final List<PitchClass> previousNotes = new ArrayList<>();
	private final List<AbsoluteChord> chords = new ArrayList<>();

	private AbsoluteChord previousChord;

	public void add(List<PitchClass> notes) {
		if(sameNotesAsPreviousFrame(notes)) {
			System.out.print(".");
			return;
		} else if(notes.isEmpty()) {
			System.out.print("_");
			return;
		} else {
			System.out.println();
		}
		previousNotes.clear();
		previousNotes.addAll(notes);

		// Not enough notes to be a triad.
		boolean notAChord = notes.size() < 3;
		if(notAChord) {
			out.println("\t " + notes);
			return;
		}

		truncateToFourNotes(notes);

		AbsoluteChord chord = new AbsoluteChord(notes);
		while (!chord.isValid() && notes.size() > 3){
			int lastIndex = notes.size() - 1;
			notes.remove(lastIndex);
			chord = new AbsoluteChord(notes);
		}

		if(chord.isValid()){

			boolean isSameChord = chord.equals(previousChord);
			if(isSameChord)
				return;
			out.println(chord + "\t" + chord.pitchClassesToString());

			chords.add(chord);

			if(chords.size() > 1) {
				calculateKey(chords);
			}
		} else {
			out.println("?\t" + chord.pitchClassesToString());
		}

		previousChord = chord;

	}

	private void truncateToFourNotes(List<PitchClass> notes) {
		while (notes.size() > 4){
			int lastIndex = notes.size() - 1;
			notes.remove(lastIndex);
		}
	}

	private boolean sameNotesAsPreviousFrame(List<PitchClass> notes) {
		return notes.size() == previousNotes.size() && notes.containsAll(previousNotes);
	}

	private boolean doesArrayContainSameNotes(Collection<PitchClass> notes1, Collection<PitchClass> notes2) {

		//TODO: Replace with Collections.containsAll(arg0)

		if(notes1.size() != notes2.size()) return false;
		for(PitchClass noteFrom2 : notes2){
			if(!notes1.contains(noteFrom2)) return false;
		}

		return true;
	}

	//Used to store a key and its precedence based on whether it contains purely diatonic, vs. chormatic chords.
	private class PossibleKey {
		final Key key;
		int precedence;

		PossibleKey(Key key, RomanNumeral chord){
			precedence = chord.getRomanNumeralType().precedence;
			this.key = key;
		}

		public Key getKey() {
			return key;
		}

		public int getPrecedence() {
			return precedence;
		}
	}

	private void calculateKey(List<AbsoluteChord> chordsInProgression){

		//If there were no chords, then the key will not be found.
		if(chordsInProgression.isEmpty()) return;

		Set<PossibleKey> possibleKeys = new HashSet<>();

		int finalIndex = chordsInProgression.size() - 1;
		int i = finalIndex;

		possibleKeys.addAll(possibleKeysFromChord(chordsInProgression.get(finalIndex)));

		while(i > 0){
			i--;
			Set<PossibleKey> matchingKeys = matchingKeys(possibleKeys, chordsInProgression.get(i));

			if(matchingKeys.isEmpty()){
				//If no keys were found, then preserve possibleKey's reference to the previous array.
				break;
			} else {
				possibleKeys = matchingKeys;
			}

			//find the least chromatic type of key
			int minPrecedence = minPrecedence(possibleKeys);

			//Remove any keys that are more chromatic than the least chromatic
			for(PossibleKey possible : possibleKeys){
				if(possible.getPrecedence() > minPrecedence) {
					possibleKeys.remove(possible);
				}
			}


			//If there is only one remaining candidate...
			if(possibleKeys.size() == 1) {
				break;
			}

		}
		String line1 = "";
		String line2 = "";
		for(PossibleKey key : possibleKeys){

			AbsoluteRomanNumeralChord chord = getAbsoluteRomanNumeral(chordsInProgression, key.getKey());
			if(chord == null) {
				continue;
			}
			line1 += String.format("%-14s", chord.getKey());
			line2 += String.format("%-14s", chord.getRomanNumeral());

		}
		out.println("\t" + line1 + "\n\t" + line2 + "\n");

	}

	private int minPrecedence(Set<PossibleKey> possibleKeys) {
		int minPrecedence = Integer.MAX_VALUE;
		for(PossibleKey key : possibleKeys){
			if(key.getPrecedence() < minPrecedence) {
				minPrecedence = key.getPrecedence();
			}
		}
		return minPrecedence;
	}

	private Set<PossibleKey> matchingKeys(Collection<PossibleKey> possibleKeys, AbsoluteChord chord) {
		Set<PossibleKey> matchingKeys = new HashSet<>();
		for(PossibleKey key : possibleKeys){
			RomanNumeral rn = key.getKey().romanNumeralFromAbsoluteChord(chord);
			if(rn == null) {
				continue;
			}

			matchingKeys.add(new PossibleKey(key.getKey(), rn));
		}
		return matchingKeys;
	}

	private Set<PossibleKey> possibleKeysFromChord(AbsoluteChord chordInProgression) {
		Set<PossibleKey> keys = new HashSet<>();
		for(Key key : Key.getAllKeys()){
			//try to create Roman numeral
			RomanNumeral rn = key.romanNumeralFromAbsoluteChord(chordInProgression);

			//If Roman numeral was not found skip to next key.
			if(rn == null) {
				continue;
			}

			//If Roman numeral was found, add this key to list of possible keys.
			keys.add(new PossibleKey(key, rn));
		}
		return keys;
	}

	/**
	 * @param chordsInProgression
	 * @param possibleKeys
	 * @param finalIndex
	 * @return
	 */
	private static AbsoluteRomanNumeralChord getAbsoluteRomanNumeral(
			List<AbsoluteChord> chordsInProgression,
			Key key) {
		int finalIndex = chordsInProgression.size() - 1;
		AbsoluteChord chord = chordsInProgression.get(finalIndex);
		RomanNumeral rn = key.romanNumeralFromAbsoluteChord(chord);
		if(rn == null) return null;
		AbsoluteRomanNumeralChord romanNumeral = new AbsoluteRomanNumeralChord(rn, key, chord);
		return romanNumeral;
	}
}
