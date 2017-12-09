package paul.wintz.music.keys;

import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.music.notes.Note;

public enum SolfegeEnum {
	DO (0, ScaleDegree.TONIC, 		IntervalEnum.UNISON),
	//	DI (1, ScaleDegree.TONIC 		IntervalEnum.AUGMENTED_UNISON),
	RA (1, ScaleDegree.SUPERTONIC,	IntervalEnum.MINOR_SECOND),
	RE (2, ScaleDegree.SUPERTONIC,	IntervalEnum.MAJOR_SECOND),
	//	RI (3, ScaleDegree.SUPERTONIC,	IntervalEnum.AUGMENTED_SECOND),
	ME (3, ScaleDegree.MEDIANT,		IntervalEnum.MINOR_THIRD),
	MI (4, ScaleDegree.MEDIANT,		IntervalEnum.MAJOR_THIRD),
	FA (5, ScaleDegree.SUBDOMINANT,	IntervalEnum.PERFECT_FOURTH),
	FI (6, ScaleDegree.SUBDOMINANT,	IntervalEnum.TRITONE),
	//	SE (6, ScaleDegree.DOMINANT,	IntervalEnum.DIMINISHED_FIFTH),
	SOL(7, ScaleDegree.DOMINANT,	IntervalEnum.PERFECT_FIFTH),
	//	SI (8, ScaleDegree.DOMINANT,	IntervalEnum.AUGMENTED_FIFTH),
	LE (8, ScaleDegree.SUBMEDIANT,	IntervalEnum.MINOR_SIXTH),
	LA (9, ScaleDegree.SUBMEDIANT,	IntervalEnum.MAJOR_SIXTH),
	//	LI (10,ScaleDegree.SUBMEDIANT,	IntervalEnum.AUGMENTED_SIXTH),
	TE (10,ScaleDegree.LEADING_TONE,IntervalEnum.MINOR_SEVENTH),
	TI (11,ScaleDegree.LEADING_TONE,IntervalEnum.MAJOR_SEVENTH);

	private final int halfstepsAboveTonic;
	private final ScaleDegree scaleDegree;
	private final IntervalEnum intervalAboveTonic;

	SolfegeEnum(int halfstepsAboveTonic, ScaleDegree scaleDegree, IntervalEnum intervalAboveTonic){
		this.halfstepsAboveTonic = halfstepsAboveTonic;
		this.scaleDegree = scaleDegree;
		this.intervalAboveTonic = intervalAboveTonic;
	}

	public ScaleDegree getScaleDegree() {
		return scaleDegree;
	}

	public int getHalfstepsAboveTonic() {
		return halfstepsAboveTonic;
	}

	public Note toNote(Note tonic){
		return Note.getNoteAtInterval(tonic, intervalAboveTonic);
	}

	@SuppressWarnings("incomplete-switch")
	static SolfegeEnum getSolfegeFromScaleDegree(ScaleDegree degree,
			ScaleDegree.Modification modificationFromMajor) throws IllegalAccessException {
		switch(degree){
		case TONIC:
			switch(modificationFromMajor){
			case NONE:
				return DO;
			case RAISED:
				//return DI;
			}
			break;
		case SUPERTONIC:
			switch(modificationFromMajor){
			case LOWERED:
				return RA;
			case NONE:
				return RE;
			case RAISED:
				//return RI;
			}
			break;
		case MEDIANT:
			switch(modificationFromMajor){
			case LOWERED:
				return ME;
			case NONE:
				return MI;
			}
			break;
		case SUBDOMINANT:
			switch(modificationFromMajor){
			case NONE:
				return FA;
			case RAISED:
				return FI;
			}
			break;
		case DOMINANT:
			switch(modificationFromMajor){
			case LOWERED:
				//return SE;
			case NONE:
				return SOL;
			case RAISED:
				//return SI;
			}
			break;
		case SUBMEDIANT:
			switch(modificationFromMajor){
			case LOWERED:
				return LE;
			case NONE:
				return LA;
			case RAISED:
				//return LI;
			}
			break;
		case LEADING_TONE:
			switch(modificationFromMajor){
			case LOWERED:
				return TE;
			case NONE:
				return TI;
			}
			break;
		}
		throw new IllegalAccessException("The scale degree '" + degree + "' is not recognized.");
	}
}

