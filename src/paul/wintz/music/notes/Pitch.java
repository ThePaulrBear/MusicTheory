package paul.wintz.music.notes;

public class Pitch {

	//Variables relating to note naming and tuning
	final static float A4 = 440;
	final static float A0 = A4 / 16;
	final static String[] NAMES_FLATS = {
			"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A"};
	final static String[] NAMES_SHARPS = {
			"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A"};

	public float frequency;
	public float noteNumber;
	public float time;
	public float strength; //not implemented

	public int octave;
	public int octaveDegree;
	public float cents;
	public String name;

	public double getFrequency() {
		return frequency;
	}

	/**
	 * Creates a NoteClass from a spectrum centered at index "i"
	 *
	 * @param spectrum
	 * @param i
	 * @param timeIn
	 */
	public Pitch(float noteNumber){
		frequency = noteToFrequency(noteNumber);
		this.setNote(noteNumber, 0);
	}

	public Pitch(float[] spectrum, int i, float timeIn){
		if(this.strength < 0) {
			System.err.println("An index that is not a maximum was used in the NoteClass constructor");
		}
		this.time = timeIn;
		this.noteNumber = Pitch.frequencyToNoteNumber(frequency);
		this.setNote(noteNumber, timeIn);
	}

	public Pitch(float inputFrequency, float strengthIn, float timeIn) {
		frequency = inputFrequency; //
		noteNumber = Pitch.frequencyToNoteNumber(frequency);
		setNote(noteNumber, timeIn);
		strength = strengthIn;
		time = timeIn; //change to time
	}

	Pitch(Pitch _anotherNote) { //does not include harmonics
		this.frequency = _anotherNote.frequency; //
		this.noteNumber = _anotherNote.noteNumber;
		this.setNote(noteNumber, _anotherNote.time);
		this.strength = _anotherNote.strength;
		this.time = _anotherNote.time; //change to time
	}

	public void setNote(float inputFrequency, float strengthIn, float timeIn) {
		noteNumber = Pitch.frequencyToNoteNumber(inputFrequency);
		setNote(noteNumber, timeIn);
		strength = strengthIn;
	}

	public void setNote(float inputNumber, float timeIn) {
		time = timeIn;
		noteNumber = inputNumber;
		frequency = Pitch.noteToFrequency(noteNumber);
		octave = (int) Math.floor( noteNumber / 12);

		//INVESTIGATE HOW THIS WORKS (IF IT EVEN DOES)
		octaveDegree = (int) ( noteNumber % 1 < 0.5 ? (noteNumber % 12) : (noteNumber % 12) + 1); // ie. A, A# B, etc.
		if (octaveDegree == 12) { //prevents error from rounding up to from B +52 cents to C -48 Cents
			octaveDegree = octaveDegree - 12;
			octave++;
		}
		if (octaveDegree >= 3)
		{
			octave++; //adjusts for octaves incrementing at C instead of A.
		}
		cents = 100 * (noteNumber % 1 < 0.5 ? noteNumber % 1 : noteNumber % 1 - 1); //ranges from 0 to .50
		name = (octaveDegree >= 0)? (NAMES_SHARPS[octaveDegree] + octave): "null";
	}

	void printNote() {
		System.out.print(toString());
	}

	@Override
	public String toString(){
		return name + ", " + noteNumber + ", " + frequency + "Hz, strength: " + strength + "\n";
	}

	static float log2 (float x) {
		float y = (float) (Math.log(x) / Math.log(2));
		return y;
	}

	//TODO: convert A0 to noteNumber 21
	public static float noteToFrequency ( float noteNumber ) {
		float frequency = (float) (Pitch.A0 * Math.pow( 2, noteNumber / 12));
		return frequency;
	}

	//TODO: convert A0 to noteNumber 21
	public static float frequencyToNoteNumber( float frequency ) {
		return 12 * Pitch.log2(frequency / Pitch.A0);
	}
}
