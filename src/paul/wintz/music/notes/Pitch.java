package paul.wintz.music.notes;

import paul.wintz.utils.Utils;

public class Pitch {

	//Variables relating to note naming and tuning
	private static final float A4 = 440;
	private static final float A0 = A4 / 16;
	private static final String[] NAMES_FLATS = {
			"A", "A_SHARP", "B", "C", "C_SHARP", "D", "D_SHARP", "E", "F", "F_SHARP", "G", "G_SHARP", "A"};
	private static final String[] NAMES_SHARPS = {
			"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A"};

	public float frequency;
	public float noteNumber;
	public float time;
	public float strength; //not implemented

	public int octave;
	public int octaveDegree;
	public float cents;
	public String name;

	public float getFrequency() {
		return frequency;
	}

	public Pitch(float noteNumber){
		frequency = noteToFrequency(noteNumber);
		this.setNote(noteNumber, 0);
	}

	public Pitch(float frequency, float strengthIn, float time) {
		this.frequency = frequency;
		noteNumber = Pitch.frequencyToNoteNumber(frequency);
		setNote(noteNumber, time);
		strength = strengthIn;
		this.time = time;
	}

	public Pitch(Pitch anotherNote) {
		this.frequency = anotherNote.frequency; //
		this.noteNumber = anotherNote.noteNumber;
		this.setNote(noteNumber, anotherNote.time);
		this.strength = anotherNote.strength;
		this.time = anotherNote.time; //change to time
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
		cents = centsFromNoteNumber(noteNumber);
		name = (octaveDegree >= 0)? (NAMES_SHARPS[octaveDegree] + octave): "null";
	}

	private float centsFromNoteNumber(float noteNumber) {
		return 100 * (noteNumber % 1 < 0.5 ? noteNumber % 1 : noteNumber % 1 - 1);
	}

	//TODO: convert A0 to noteNumber 21
	public static float noteToFrequency ( float noteNumber ) {
		float frequency = (float) (Pitch.A0 * Math.pow( 2, noteNumber / 12));
		return frequency;
	}

	//TODO: convert A0 to noteNumber 21
	public static float frequencyToNoteNumber( float frequency ) {
		return 12 * Utils.log2(frequency / Pitch.A0);
	}

	@Override
	public String toString(){
		return name + ", " + noteNumber + ", " + frequency + "Hz, strength: " + strength;
	}
}
