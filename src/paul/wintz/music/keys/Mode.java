package paul.wintz.music.keys;

import java.util.*;

import paul.wintz.music.chords.*;
import paul.wintz.music.notes.*;

public enum Mode {
	MAJOR (
			RomanNumeral.Major.MAJOR,
			SolfegeEnum.DO,
			SolfegeEnum.RE,
			SolfegeEnum.MI,
			SolfegeEnum.FA,
			SolfegeEnum.SOL,
			SolfegeEnum.LA,
			SolfegeEnum.TI
			),
	MINOR (
			RomanNumeral.Minor.MINOR,
			SolfegeEnum.DO,
			SolfegeEnum.RE,
			SolfegeEnum.MI,
			SolfegeEnum.FA,
			SolfegeEnum.SOL,
			SolfegeEnum.LE,
			SolfegeEnum.TE);

	private final SolfegeEnum[] solfegeEnum;
	private final RomanNumeral[] relativeChords;

	Mode (RomanNumeral[] diatonicQualities, SolfegeEnum... solfege){
		this.solfegeEnum = solfege;
		this.relativeChords = diatonicQualities;
	}

	RomanNumeral getDiatonicChordQuality(ScaleDegree deg){
		return relativeChords[deg.ordinal()];
	}


	//GETTERS
	int getHalfstepsAboveTonic(ScaleDegree deg){
		return solfegeEnum[deg.ordinal()].getHalfstepsAboveTonic();
	}

	//Calculations
	//Check each
	ArrayList<RomanNumeral> getMatchingRelativeChords(PitchClass tonic, AbsoluteChord chordToMatch){
		ArrayList<RomanNumeral> matchingChords = new ArrayList<>();

		int targetHalfStepsAboveTonic = PitchClass.getDifferneceInHalfSteps(tonic, chordToMatch.getRoot());

		if(targetHalfStepsAboveTonic < 0) {
			targetHalfStepsAboveTonic += 12;
		}

		for(RomanNumeral chordToCheck : relativeChords){
			if(targetHalfStepsAboveTonic != chordToCheck.getHalfStepsAboveTonic()) {
				continue;
			}

			if(Chord.isSameQuality(chordToCheck, chordToMatch)){
				matchingChords.add(chordToCheck);
			}

			if(chordToCheck.quality.isSeventhChord()){

			}
		}
		return matchingChords;
	}

	public List<NoteClass> getNotes(NoteClass tonic){
		List<NoteClass>  noteClass = new ArrayList<>();

		for(SolfegeEnum slfg : getSolfege()){
			noteClass.add(slfg.toNote(tonic));
		}

		return noteClass;
	}

	public RomanNumeral[] getRelativeChords() {
		return relativeChords;
	}

	public SolfegeEnum[] getSolfege() {
		return solfegeEnum;
	}

	public String notesToString(NoteClass tonic){
		StringBuilder sb = new StringBuilder();
		for(NoteClass noteClass : getNotes(tonic)){
			sb.append(noteClass.getName()).append(" ");
		}
		return sb.toString();
	}

	@Override
	public String toString(){
		return name().toLowerCase().replace("_", " ");
	}
}
