package paul.wintz.music.keys;

import java.util.*;

import paul.wintz.music.chords.*;
import paul.wintz.music.notes.*;

public enum Mode {
	MAJOR (
			RomanNumeralChord.Major.MAJOR,
			SolfegeEnum.DO,
			SolfegeEnum.RE,
			SolfegeEnum.MI,
			SolfegeEnum.FA,
			SolfegeEnum.SOL,
			SolfegeEnum.LA,
			SolfegeEnum.TI
			),
	MINOR (
			RomanNumeralChord.Minor.MINOR,
			SolfegeEnum.DO,
			SolfegeEnum.RE,
			SolfegeEnum.MI,
			SolfegeEnum.FA,
			SolfegeEnum.SOL,
			SolfegeEnum.LE,
			SolfegeEnum.TE);

	private final SolfegeEnum[] solfegeEnum;
	private final RomanNumeralChord[] relativeChords;

	Mode (RomanNumeralChord[] diatonicQualities, SolfegeEnum... solfege){
		this.solfegeEnum = solfege;
		this.relativeChords = diatonicQualities;
	}

	RomanNumeralChord getDiatonicChordQuality(ScaleDegree deg){
		return relativeChords[deg.ordinal()];
	}


	//GETTERS
	int getHalfstepsAboveTonic(ScaleDegree deg){
		return solfegeEnum[deg.ordinal()].getHalfstepsAboveTonic();
	}

	//Calculations
	//Check each
	ArrayList<RomanNumeralChord> getMatchingRelativeChords(PitchClass tonic, AbsoluteChord chordToMatch){
		ArrayList<RomanNumeralChord> matchingChords = new ArrayList<>();

		int targetHalfStepsAboveTonic = PitchClass.getDifferneceInHalfSteps(tonic, chordToMatch.getRoot());

		if(targetHalfStepsAboveTonic < 0) {
			targetHalfStepsAboveTonic += 12;
		}

		for(RomanNumeralChord chordToCheck : relativeChords){
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

	public List<Note> getNotes(Note tonic){
		List<Note>  notes = new ArrayList<>();

		for(SolfegeEnum slfg : getSolfege()){
			notes.add(slfg.toNote(tonic));
		}

		return notes;
	}

	public RomanNumeralChord[] getRelativeChords() {
		return relativeChords;
	}

	public SolfegeEnum[] getSolfege() {
		return solfegeEnum;
	}

	public String notesToString(Note tonic){
		StringBuilder sb = new StringBuilder();
		for(Note note : getNotes(tonic)){
			sb.append(note.getName()).append(" ");
		}
		return sb.toString();
	}

	@Override
	public String toString(){
		return name().toLowerCase().replace("_", " ");
	}
}
