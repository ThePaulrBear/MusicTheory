/**
 *
 */
package paul.wintz.music.notes;

import paul.wintz.music.intervals.IntervalEnum;

/**
 * A pitchClass is a note with a specified name.
 * For example, the pitch class 1 (C#), could be the pitchClass
 * Bx, C#, or Db, but these are three distinct NoteClasses.
 *
 * NoteClasses are distinct from notes in that they are agnostic
 * about octaves, so A#2 and A#4 would be the same NoteClass: A#
 */
public class NoteClass {
	private final PitchClass pitchClass;
	private final NoteLetter noteLetter;
	private final Accidental accidental;
	private final boolean isDefaultName;

	public NoteClass(NoteLetter noteLetter, Accidental accidental){
		this.noteLetter = noteLetter;
		this.accidental = accidental;
		pitchClass = noteLetter.getBaseNote().addHalfsteps(accidental.shift);
		isDefaultName = false;
	}

	//Get default NoteName for a PitchClass
	public NoteClass(PitchClass note){
		this.noteLetter = NoteLetter.pitchClassToBase(note);
		this.accidental = Accidental.accidentalFromBaseAndPitchClass(noteLetter, note);
		this.pitchClass = note;
		this.isDefaultName = true;
	}

	public int getNoteNumber(){
		return this.pitchClass.getNoteNumber();
	}

	public String getName(){
		return noteLetter.name() + accidental.getSymbol();
	}

	public PitchClass getNote() {
		return pitchClass;
	}

	public NoteLetter getBase() {
		return noteLetter;
	}

	public Accidental getAccidental() {
		return accidental;
	}

	public boolean isDefaultName() {
		return isDefaultName;
	}

	@Override
	public String toString(){
		return noteLetter.name() + accidental.symbol;
	}

	public static NoteClass getNoteAtInterval(NoteClass root, IntervalEnum intervalEnum) {

		int newOrdinal = (root.noteLetter.ordinal() + intervalEnum.getScaleSteps()) % NoteLetter.values().length;
		NoteLetter newBase = NoteLetter.values()[newOrdinal];
		PitchClass newNote = root.pitchClass.addInterval(intervalEnum);

		try {
			Accidental newAccidental = Accidental.accidentalFromBaseAndPitchClass(newBase, newNote);
			return new NoteClass(newBase, newAccidental);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return new NoteClass(newNote);
		}

		//		int halfStepsBetween = PitchClass.getDifferneceInHalfSteps(root.getNoteNumber(), newBase.getNoteNumber());
		//
		//		int targetHalfSteps = interval.getHalfSteps();
		//
		//		int accidentalShift = (targetHalfSteps - halfStepsBetween);
		////		while(accidentalShift >= 10) accidentalShift -= 12;
		////		if(accidentalShift > 2 || accidentalShift < -2)
		////			throw new Exception
		//		String errorMsg = "\nRoot: " + root.toString()
		//			+ "\nInterval: " + interval.toString()
		//			+ "\nNew NoteLetter: " + newBase.toString()
		//			+ "\nTarget Half Steps: " + targetHalfSteps
		//			+ "\nHalf Steps Between: " + halfStepsBetween
		//			+ "\nAccidental Shift: " + accidentalShift
		//			;
		//

		//		Accidental newAccidental;
		//		try {
		//			newAccidental = Accidental.accidentalFromShift(accidentalShift);
		//		} catch (IllegalArgumentException e) {
		//			new IllegalArgumentException(e.getMessage() + errorMsg).printStackTrace();
		//			return NoteName()
		//		}


		//		out.println("Second pitchClass newBase.name() + newAccidental.symbol");
		//		return new NoteName(newBase, newAccidental);
	}
}
