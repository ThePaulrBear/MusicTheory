package paul.wintz.music.notes;

public enum NoteLetter {
	C (PitchClass.C),
	D (PitchClass.D),
	E (PitchClass.E),
	F (PitchClass.F),
	G (PitchClass.G),
	A (PitchClass.A),
	B (PitchClass.B);

	private final PitchClass baseNote;

	private NoteLetter(PitchClass baseNote) {
		this.baseNote = baseNote;
	}

	PitchClass getBaseNote() {
		return baseNote;
	}

	int getNoteNumber(){
		return baseNote.getNoteNumber();
	}

	static NoteLetter pitchClassToBase(PitchClass note){
		switch(note){
		case A:
		case G_SHARP:
			return A;
		case B:
		case A_SHARP:
			return B;
		case C:
			return C;
		case D:
		case C_SHARP:
			return D;
		case E:
		case D_SHARP:
			return E;
		case F:
			return F;
		case G:
		case F_SHARP:
			return G;
		default:
			return null;
		}
	}

}