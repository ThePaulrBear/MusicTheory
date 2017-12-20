package paul.wintz.music.notes;

import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.utils.exceptions.UnhandledCaseException;

public enum PitchClass {
	//Flats are used because octothorpes, #'s, do not work.
	//The names used here are only for identification,
	C, Db , D, Eb, E, F, Gb, G, Ab, A, Bb, B;

	private enum AccidentalType{
		FLAT, SHARP;
	}

	//Returns the number of half-steps above C.
	public int getNoteNumber(){
		return this.ordinal();
	}

	public PitchClass addHalfsteps(int halfSteps){
		int newNoteNumber = (this.getNoteNumber() + halfSteps) % 12;
		while(newNoteNumber < 0) {
			newNoteNumber += 12;
		}

		return PitchClass.values()[newNoteNumber];
	}
	public PitchClass addInterval(IntervalEnum intervalEnum){
		return PitchClass.values()[(this.getNoteNumber() + intervalEnum.getHalfSteps()) % 12];
	}

	public static PitchClass getFromMidiNumber(int midiNumber){
		return PitchClass.values()[(60 + midiNumber) % 12];
	}

	public static int getDifferneceInHalfSteps(PitchClass first, PitchClass second){
		return getDifferneceInHalfSteps(first.getNoteNumber(), second.getNoteNumber());
	}

	public static int getDifferneceInHalfSteps(int noteNumber1, int noteNumber2) {
		return noteNumber2 - noteNumber1;
	}

	public String getName(PitchClass pitchClass, AccidentalType accidentalPreference){
		switch(pitchClass){
		case C:
			return "C";
		case Db:
			switch (accidentalPreference) {
			case SHARP:
				return "C#";
			case FLAT:
				return "Db";
			}
			break;
		case D:
			return "D";
		case Eb:
			switch (accidentalPreference) {
			case SHARP:
				return "D#";
			case FLAT:
				return "Eb";
			}
			break;
		case E:
			return "E";
		case F:
			return "F";
		case Gb:
			switch (accidentalPreference) {
			case SHARP:
				return "F#";
			case FLAT:
				return "Gb";
			}
			break;
		case G:
			return "G";
		case Ab:
			switch (accidentalPreference) {
			case SHARP:
				return "G#";
			case FLAT:
				return "Ab";
			}
			break;
		case A:
			return "A";
		case Bb:
			switch (accidentalPreference) {
			case SHARP:
				return "A#";
			case FLAT:
				return "Bb";
			}
			break;
		case B:
			return "B";
		}

		throw new UnhandledCaseException(pitchClass);
	}
}
