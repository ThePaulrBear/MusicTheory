package paul.wintz.music.chords;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.System.out;

import java.util.*;

import com.google.common.base.Preconditions;

import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.music.keys.Key;
import paul.wintz.music.notes.PitchClass;

/**
 * @author PaulWintz
 *
 * Absolute Chord contains the root, quality and notes of
 * a specific chord, (e.g. G min, Bb dim7, C+).
 */
public class AbsoluteChord extends Chord {

	private PitchClass root;
	private List<PitchClass> notes = new ArrayList<>();
	private List<IntervalEnum> intervalEnums = new ArrayList<>();

	/**
	 * @param notes
	 * @precondition
	 * 	!notes.isEmpty()
	 * @throws IllegalArgumentException
	 */
	public AbsoluteChord(List<PitchClass> notes) {
		checkArgument(!notes.isEmpty(), "Notes array was empty");

		this.notes = notes;
		removeDuplicates(notes);
		this.root = findRoot(this.notes);
		sortNotesOfChord();

		for(int i = 0; i < notes.size(); i++){

			IntervalEnum thirdType = IntervalEnum.findThirdType(notes);
			IntervalEnum fifthType = IntervalEnum.findFifthType(notes);
			IntervalEnum seventhType = IntervalEnum.findSeventhType(notes);
			IntervalEnum ninthType = IntervalEnum.findNinthType(notes);
			IntervalEnum eleventhType = IntervalEnum.findEleventhType(notes);
			IntervalEnum thirteenthType = IntervalEnum.findThirteenthType(notes);

			intervalEnums.add(thirdType);
			intervalEnums.add(fifthType);
			intervalEnums.add(seventhType);
			intervalEnums.add(ninthType);
			intervalEnums.add(eleventhType);
			intervalEnums.add(thirteenthType);

			quality = ChordQuality.calculate(thirdType, fifthType, seventhType, ninthType, eleventhType, thirteenthType);

			if(quality.isValid()) {
				break;
			}

			intervalEnums.clear();
			//If chord is not valid, try another note for the root.
			notes.add(notes.get(0));
			notes.remove(0);
			root = notes.get(0);
		}
	}

	private void printDiagnosticInfo() {
		out.println("--------------\n"
				+ "Notes: " + pitchClassesToString()
				+ "\nIntervals");
		for(IntervalEnum inter : intervalEnums){
			out.print(inter + " ");
		}

		out.println("\nChord Quality : " + quality.toString());
	}

	/**
	 * @param notes
	 * @precondition
	 * 	!notes.isEmpty()
	 * @throws IllegalArgumentException
	 */
	/*public AbsoluteChord(PitchClass... notes) {
		if(notes.length == 0) throw new IllegalArgumentException("Chord cannot be constructed. Notes array was empty.");

		for(PitchClass note : notes){
			this.notes.add(note);
		}

		removeDuplicates(this.notes);
		this.root = findRoot(this.notes);
		sortNotesOfChord();

		int length = this.notes.size();
		for(int i = 0; i < length; i++){
			IntervalEnum thirdType = IntervalEnum.findThirdType(this.notes);
			IntervalEnum fifthType = IntervalEnum.findFifthType(this.notes);
			IntervalEnum seventhType = IntervalEnum.findSeventhType(this.notes);
			IntervalEnum ninthType = IntervalEnum.findNinthType(this.notes);

			quality = ChordQuality.calculate(thirdType, fifthType, seventhType, ninthType);


			if(quality.isValid()) break;
			this.notes.add(this.notes.get(0));
			this.notes.remove(0);
			root = this.notes.get(0);
		}
	}
	 */
	public AbsoluteChord(PitchClass root, ChordQuality quality)  {
		this.root = root;
		this.quality = quality;

		if(this.isValid()){
			notes.add(root);
			for(IntervalEnum intervalEnum : this.quality.getIntervals()){
				notes.add(root.addInterval(intervalEnum));
			}
		}
	}

	/**
	 * Finds the most likely root of chord
	 *
	 * @param notes
	 * @return
	 * 	The the root of the chord.
	 */
	private static PitchClass findRoot(List<PitchClass> notes) {
		//For each note in the chord, find the sum of the odd-numbered intervalEnums up to each other note in the chord.
		//The note that has the smallest sum will be the root.

		int[] sums = new int[notes.size()];
		int minSum = Integer.MAX_VALUE;
		int indexSmallestSum = 0;

		for(int i = 0; i < notes.size(); i++){
			for(int j = 0; j < notes.size(); j++){
				IntervalEnum intervalEnum = IntervalEnum.findOddIntervalUp(notes.get(i), notes.get(j));

				//No need to compare the note to itself.
				if(j == i) {
					continue;
				}
				sums[i] += intervalEnum.getHalfSteps();
			}
			if(sums[i] < minSum) {
				minSum = sums[i];
				indexSmallestSum = i;
			}
		}
		return notes.get(indexSmallestSum);
	}

	/**
	 * Removes all duplicate notes form the array.
	 * @param notes
	 * The array of notes
	 */
	private static void removeDuplicates(List<PitchClass> notes){
		for(int i = 0; i < notes.size(); i++){

			for (int j = i+1; j < notes.size(); j++) {
				//If note is already in the array, remove it.
				Preconditions.checkState(i != j, "NoteClass was checked against itself");
				if (notes.get(i).ordinal() == notes.get(j).ordinal()) {
					notes.remove(j);
					j--;
					continue;
				}
			}
		}
	}

	public class PitchClassComparator implements Comparator<PitchClass>
	{
		@Override
		public int compare(PitchClass note1, PitchClass note2)
		{
			if (IntervalEnum.findOddIntervalUp(root, note1).getHalfSteps() < IntervalEnum.findOddIntervalUp(root, note2).getHalfSteps())
				return -1;
			else if (IntervalEnum.findOddIntervalUp(root, note1).getHalfSteps() > IntervalEnum.findOddIntervalUp(root, note2).getHalfSteps())
				return 1;
			else
				return 0;
		}
	}

	private void sortNotesOfChord(){
		PitchClassComparator c = new PitchClassComparator();
		Collections.sort(notes, c);
	}

	public void printNotes() {
		for(int i = 0; i < notes.size(); i++){
			System.out.print(notes.get(i) + " ");
		}
	}

	private void printIntervalsAboveRoot(){
		for (int i = 0; i < notes.size(); i++) {
			System.out.print(IntervalEnum.findOddIntervalUp(notes.get(0), notes.get(i)) + " ");
		}
		System.out.println();
	}

	public static void test() {

		for (int j = 0; j < 1; j++) {
			List<PitchClass> notes = new ArrayList<>();

			notes.add(PitchClass.C);
			notes.add(PitchClass.E);
			notes.add(PitchClass.Ab);

			AbsoluteChord chord = new AbsoluteChord(notes);

			if(chord.quality.isValid()) {
				System.out.print("\n" + chord.toString() + ": ");
				chord.printNotes();
			}
		}
	}

	private static void testFindOddInterval() {
		for (int j = 0; j < 12; j++) {
			for (int i = 0; i < 12; i++) {
				PitchClass bottom = PitchClass.values()[j % 12];
				PitchClass top = PitchClass.values()[i % 12];
				System.out.println("bottom: " + bottom + ", Top: " + top + ". IntervalEnum: " + IntervalEnum.findOddIntervalUp(bottom, top));
			}
		}
	}

	private static void testIntervals(){
		for (int i = 0; i < IntervalEnum.values().length; i++) {
			for (int j = i; j < IntervalEnum.values().length; j++) {

				IntervalEnum intervalEnum = IntervalEnum.getDefaultInterval(i, j);
				System.out.println("bottom: " + i + ", Top: " + j + ". IntervalEnum: " + intervalEnum);
			}
		}
	}

	static String calculateName(PitchClass root, ChordQuality quality){
		return root.name() + quality.getSufix();
	}

	public PitchClass getRoot() {
		return root;
	}

	public List<PitchClass> getNotes() {
		return notes;
	}

	/**
	 * @return Returns true if the chord does not have multiple notes at
	 * given interval (a minor and a major third, for example
	 */
	public boolean isValid(){
		return quality.isValid();
	}

	public String pitchClassesToString(){
		StringBuilder sb = new StringBuilder();
		for(PitchClass note : notes){
			sb.append(" ").append(note.name());
		}
		return sb.toString();
	}

	@Override
	public String toString(){
		return root.name() + quality.getSufix();
	}


	public List<AbsoluteRomanNumeralChord> getAllPossibleRomanNumerals(){
		return getPossibleRomanNumerals(Key.getAllKeys());
	}

	public List<AbsoluteRomanNumeralChord> getPossibleRomanNumerals(List<Key> keysToCheck){
		List<AbsoluteRomanNumeralChord> matchingRomanNumerals = new ArrayList<>();

		//For each key
		for(Key key : keysToCheck){
			//Find the matching Roman numeral.
			RomanNumeralChord romanNumeral = key.romanNumeralFromAbsoluteChord(this);

			//If chord is not contained in key, continue.
			if(romanNumeral == null) {
				continue;
			}

			//Otherwise, add it to an array
			AbsoluteRomanNumeralChord absoluteRomanNumeralChord = new AbsoluteRomanNumeralChord(romanNumeral, key, this);
			matchingRomanNumerals.add(absoluteRomanNumeralChord);
		}

		return matchingRomanNumerals;
	}


	/**
	 * Finds each key that contains this chord.
	 * @param keysToCheck
	 * @return
	 */
	public List<Key> getContainingKeys(List<Key> keysToCheck){
		List<Key> containingKey = new ArrayList<>();

		//For each key
		for(Key key : keysToCheck){
			//Find the matching Roman numeral.

			//If chord is contained in key, add key to the array.
			if(key.containsChord(this)) {
				containingKey.add(key);
			}
		}

		return containingKey;
	}

	@Override
	public boolean equals(Object obj) {

		if(!(obj instanceof AbsoluteChord))
			return false;

		AbsoluteChord other = (AbsoluteChord) obj;

		return this.quality == other.quality && this.root == other.root;
	}
}
