/**
 * 
 */
package paul.wintz.music.notes;

import paul.wintz.music.UnicodSymbol;
import paul.wintz.music.intervals.IntervalEnum;

/**
 * A pitchClass is a pitch class with a specified name.
 * For example, the pitch class 1 (C#), could be the pitchClass 
 * Bx, C#, or Db. All these possibilities are unique. 
 * 
 * @author PaulWintz
 *
 */
public class NoteClass {
	private final PitchClass pitchClass;
	private final NaturalPitchClass naturalPitchClass;
	private final Accidental accidental;
	private final boolean isDefaultName;
	
	public NoteClass(NaturalPitchClass naturalPitchClass, Accidental accidental){
		this.naturalPitchClass = naturalPitchClass;
		this.accidental = accidental;
		pitchClass = naturalPitchClass.getBaseNote().addHalfsteps(accidental.shift);
		isDefaultName = false;
	}
	
	//Get default NoteName for a PitchClass
	public NoteClass(PitchClass note){
		this.naturalPitchClass = NaturalPitchClass.pitchClassToBase(note);
		this.accidental = Accidental.accidentalFromBaseAndPitchClass(naturalPitchClass, note);
		this.pitchClass = note;
		this.isDefaultName = true;
	}
	
	public int getNoteNumber(){
		return this.pitchClass.getNoteNumber();
	}
	
	public String getName(){
		return naturalPitchClass.name() + accidental.getSymbol();
	}
	
	public PitchClass getNote() {
		return pitchClass;
	}

	public NaturalPitchClass getBase() {
		return naturalPitchClass;
	}

	public Accidental getAccidental() {
		return accidental;
	}

	public boolean isDefaultName() {
		return isDefaultName;
	}

	@Override
	public String toString(){
		return naturalPitchClass.name() + accidental.symbol;
	}
	
	public static NoteClass getNoteAtInterval(NoteClass root, IntervalEnum intervalEnum) {
		
		int newOrdinal = (root.naturalPitchClass.ordinal() + intervalEnum.getScaleSteps()) % NaturalPitchClass.values().length;
		NaturalPitchClass newBase = NaturalPitchClass.values()[newOrdinal];
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
//			+ "\nNew NaturalPitchClass: " + newBase.toString()
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
	
	
	
	public enum Accidental {
		DOUBLE_FLAT (-2, UnicodSymbol.DOUBLE_FLAT), 
		FLAT (-1, UnicodSymbol.FLAT), 
		NATURAL (0, UnicodSymbol.NATURAL), 
		SHARP (+1, UnicodSymbol.SHARP), 
		DOUBLE_SHARP (+2, UnicodSymbol.DOUBLE_SHARP);
		
		private final int shift;
		private final String symbol;
		
		private Accidental(int shift, String symbol) {
			this.shift = shift;
			this.symbol = symbol;
		}

		private int getShift() {
			return shift;
		}

		private String getSymbol() {
			return symbol;
		}	
		
		private static Accidental accidentalFromBaseAndPitchClass(NaturalPitchClass naturalPitchClass, PitchClass note) throws IllegalArgumentException{
			int halfStepsBetween = PitchClass.getDifferneceInHalfSteps(naturalPitchClass.getNoteNumber(), note.getNoteNumber());
						
			int accidentalShift = halfStepsBetween;

			String errorMsg = "\nNote: " + note.toString() 
				+ "\nBase: " + naturalPitchClass.toString()
				+ "\nHalf Steps Between: " + halfStepsBetween
				+ "\nAccidental Shift: " + accidentalShift
				; 
			
//			try {
				return Accidental.accidentalFromShift(accidentalShift);
//			} catch (IllegalArgumentException e) {
//				throw new IllegalArgumentException(e.getMessage() + errorMsg);
//			}
		}
		
		/**
		 * 
		 * @param shift 
		 * 		Must be between -2 and +2.
		 * @return accidental 
		 * 		Accidental that gives the shift specified in parameter.
		 * 		
		 */
		public static Accidental accidentalFromShift(int shift) throws IllegalArgumentException{
			shift %= 12;
			
			switch(shift){
			case -2:
			case 10:
				return DOUBLE_FLAT;
			case -1:
			case 11:
				return FLAT;
			case 0:
			case 12:
				return NATURAL;
			case +1:
			case -11:
				return SHARP;
			case +2:
			case -10:
				return DOUBLE_SHARP;
			default:
				throw new IllegalArgumentException("The shift, " + shift + ", was too far to be handled by an accidental");
			}
		}
	}
	
	
	
	
//	public String getName(int ordinal){
//		return NoteName.values()[ordinal].name();
//	}
}
