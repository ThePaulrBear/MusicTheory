package paul.wintz.music.notes;

public enum NaturalPitchClass {
	C (PitchClass.C), 
	D (PitchClass.D), 
	E (PitchClass.E), 
	F (PitchClass.F), 
	G (PitchClass.G), 
	A (PitchClass.A), 
	B (PitchClass.B);
	
	private final PitchClass baseNote;

	private NaturalPitchClass(PitchClass baseNote) {
		this.baseNote = baseNote;
	}

	PitchClass getBaseNote() {
		return baseNote;
	}
	
	int getNoteNumber(){
		return baseNote.getNoteNumber();
	}
	
	static NaturalPitchClass pitchClassToBase(PitchClass note){
		switch(note){
		case A:
		case Ab:
			return A;
		case B:
		case Bb:
			return B;
		case C:
			return C;
		case D:
		case Db:
			return D;
		case E:
		case Eb:
			return E;
		case F:
			return F;
		case G:
		case Gb:
			return G;
		default:
			return null;
		}
	}
	
	private static int getDifferenceInHalfSteps(NaturalPitchClass first, NaturalPitchClass second){
		return PitchClass.getDifferneceInHalfSteps(first.getBaseNote(), second.getBaseNote());
	}
}