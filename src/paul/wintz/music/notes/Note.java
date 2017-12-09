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
public class Note {
	private final PitchClass pitchClass;
	private final Base base;
	private final Accidental accidental;
	private final boolean isDefaultName;
	
	public Note(Base base, Accidental accidental){
		this.base = base;
		this.accidental = accidental;
		pitchClass = base.getBaseNote().addHalfsteps(accidental.shift);
		isDefaultName = false;
	}
	
	//Get default NoteName for a PitchClass
	public Note(PitchClass note){
		this.base = Base.pitchClassToBase(note);
		this.accidental = Accidental.accidentalFromBaseAndPitchClass(base, note);
		this.pitchClass = note;
		this.isDefaultName = true;
	}
	
	public int getNoteNumber(){
		return this.pitchClass.getNoteNumber();
	}
	
	public String getName(){
		return base.name() + accidental.getSymbol();
	}
	
	public PitchClass getNote() {
		return pitchClass;
	}

	public Base getBase() {
		return base;
	}

	public Accidental getAccidental() {
		return accidental;
	}

	public boolean isDefaultName() {
		return isDefaultName;
	}

	@Override
	public String toString(){
		return base.name() + accidental.symbol;
	}
	
	public static Note getNoteAtInterval(Note root, IntervalEnum intervalEnum) {
		
		int newOrdinal = (root.base.ordinal() + intervalEnum.getScaleSteps()) % Base.values().length;
		Base newBase = Base.values()[newOrdinal];
		PitchClass newNote = root.pitchClass.addInterval(intervalEnum); 
				
		try {
			Accidental newAccidental = Accidental.accidentalFromBaseAndPitchClass(newBase, newNote);
			return new Note(newBase, newAccidental);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return new Note(newNote);
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
//			+ "\nNew Base: " + newBase.toString()
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
	
	
	
	public enum Base {
		C (PitchClass.C), 
		D (PitchClass.D), 
		E (PitchClass.E), 
		F (PitchClass.F), 
		G (PitchClass.G), 
		A (PitchClass.A), 
		B (PitchClass.B);
		
		private final PitchClass baseNote;

		private Base(PitchClass baseNote) {
			this.baseNote = baseNote;
		}

		private PitchClass getBaseNote() {
			return baseNote;
		}
		
		private int getNoteNumber(){
			return baseNote.getNoteNumber();
		}
		
		static Base pitchClassToBase(PitchClass note){
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
		
		private static int getDifferenceInHalfSteps(Base first, Base second){
			return PitchClass.getDifferneceInHalfSteps(first.getBaseNote(), second.getBaseNote());
		}
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
		
		private static Accidental accidentalFromBaseAndPitchClass(Base base, PitchClass note) throws IllegalArgumentException{
			int halfStepsBetween = PitchClass.getDifferneceInHalfSteps(base.getNoteNumber(), note.getNoteNumber());
						
			int accidentalShift = halfStepsBetween;

			String errorMsg = "\nNote: " + note.toString() 
				+ "\nBase: " + base.toString()
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
