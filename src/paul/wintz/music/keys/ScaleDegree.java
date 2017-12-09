package paul.wintz.music.keys;

import paul.wintz.music.chords.ChordQuality;
import paul.wintz.music.intervals.IntervalEnum;

public enum ScaleDegree {
	TONIC ("I"), 
	SUPERTONIC ("II"), 
	MEDIANT ("III"), 
	SUBDOMINANT ("IV"), 
	DOMINANT ("V"), 
	SUBMEDIANT ("VI"), 
	LEADING_TONE ("VII");

	enum Modification {
		LOWERED, NONE, RAISED;
	}
	
	final String romanNumeral;
	
	ScaleDegree(String romanNumeral){
		this.romanNumeral = romanNumeral;
	}
	
	int getNumber(){
		return this.ordinal();
	}
	
	/**
	 * Returns the Roman numeral of the scale degree. Capitalized if major or augmented, 
	 * lower-case if minor or diminished.  
	 * @param quality
	 * @return
	 */
	public String getRomanNumeral(ChordQuality quality) {
		if(!quality.isValid()) throw new IllegalArgumentException("Invalid Chord Quality");
		
		String s = this.romanNumeral;
		
		if (quality.getThird() == IntervalEnum.MAJOR_TENTH)
			s = s.toUpperCase();
		else 
			s = s.toLowerCase();

		s += quality.getSufix();
		
		return s;
	}
}
