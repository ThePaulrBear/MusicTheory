package paul.wintz.music.notes;

import paul.wintz.music.UnicodSymbol;
import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.utils.exceptions.UnhandledCaseException;

public enum PitchClass {
	//Flats are used because octothorpes, #'s, do not work.
	//The names used here are only for identification,
	C, C_SHARP , D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B;

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
		case C_SHARP:
			switch (accidentalPreference) {
			case SHARP:
				return "C#";
			case FLAT:
				return "D" + UnicodSymbol.FLAT;
			}
			break;
		case D:
			return "D";
		case D_SHARP:
			switch (accidentalPreference) {
			case SHARP:
				return "D#";
			case FLAT:
				return "E" + UnicodSymbol.FLAT;
			}
			break;
		case E:
			return "E";
		case F:
			return "F";
		case F_SHARP:
			switch (accidentalPreference) {
			case SHARP:
				return "F#";
			case FLAT:
				return "G" + UnicodSymbol.FLAT;
			}
			break;
		case G:
			return "G";
		case G_SHARP:
			switch (accidentalPreference) {
			case SHARP:
				return "G#";
			case FLAT:
				return "A" + UnicodSymbol.FLAT;
			}
			break;
		case A:
			return "A";
		case A_SHARP:
			switch (accidentalPreference) {
			case SHARP:
				return "A#";
			case FLAT:
				return "B" + UnicodSymbol.FLAT;
			}
			break;
		case B:
			return "B";
		}

		throw new UnhandledCaseException(pitchClass);
	}


	@Override
	public String toString() {
		return getName(this, AccidentalType.FLAT);
	}


}
