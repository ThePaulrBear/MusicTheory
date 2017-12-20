package paul.wintz.music.keys;

import static paul.wintz.music.intervals.IntervalEnum.*;
import static paul.wintz.music.keys.ScaleDegree.*;

import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.music.notes.NoteClass;

public enum Solfege {
	DO (0, TONIC, 		UNISON),
	//DI (1, TONIC, 		AUGMENTED_UNISON),
	RA (1, SUPERTONIC,	MINOR_SECOND),
	RE (2, SUPERTONIC,	MAJOR_SECOND),
	//	RI (3, SUPERTONIC,	AUGMENTED_SECOND),
	ME (3, MEDIANT,		MINOR_THIRD),
	MI (4, MEDIANT,		MAJOR_THIRD),
	FA (5, SUBDOMINANT,	PERFECT_FOURTH),
	FI (6, SUBDOMINANT,	TRITONE),
	//	SE (6, DOMINANT,	DIMINISHED_FIFTH),
	SOL(7, DOMINANT,	PERFECT_FIFTH),
	//	SI (8, DOMINANT,	AUGMENTED_FIFTH),
	LE (8, SUBMEDIANT,	MINOR_SIXTH),
	LA (9, SUBMEDIANT,	MAJOR_SIXTH),
	//	LI (10,SUBMEDIANT,	AUGMENTED_SIXTH),
	TE (10,LEADING_TONE,MINOR_SEVENTH),
	TI (11,LEADING_TONE,MAJOR_SEVENTH);

	private final int halfstepsAboveTonic;
	private final ScaleDegree scaleDegree;
	private final IntervalEnum intervalAboveTonic;

	private Solfege(int halfstepsAboveTonic, ScaleDegree scaleDegree, IntervalEnum intervalAboveTonic){
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

	public NoteClass toNote(NoteClass tonic){
		return NoteClass.getNoteAtInterval(tonic, intervalAboveTonic);
	}

	@SuppressWarnings("incomplete-switch")
	static Solfege getSolfegeFromScaleDegree(ScaleDegree degree,
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

