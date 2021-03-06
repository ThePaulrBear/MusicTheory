package paul.wintz.music.keys;

import static paul.wintz.music.keys.Solfege.*;

import java.util.*;

import paul.wintz.music.chords.*;
import paul.wintz.music.notes.*;

public enum Mode {
	MAJOR (
			RomanNumeral.Major.MAJOR,
			DO, RE, MI, FA, SOL, LA, TI),
	MINOR (
			RomanNumeral.Minor.MINOR,
			DO, RE, ME, FA, SOL, LE, TE);

	private final Solfege[] solfege;
	private final RomanNumeral[] relativeChords;

	private Mode(RomanNumeral[] diatonicQualities, Solfege... solfege){
		this.solfege = solfege;
		this.relativeChords = diatonicQualities;
	}

	RomanNumeral getDiatonicChordQuality(ScaleDegree deg){
		return relativeChords[deg.ordinal()];
	}


	//GETTERS
	int getHalfstepsAboveTonic(ScaleDegree deg){
		return solfege[deg.ordinal()].getHalfstepsAboveTonic();
	}

	List<RomanNumeral> getMatchingRelativeChords(PitchClass tonic, AbsoluteChord chordToMatch){
		List<RomanNumeral> matchingChords = new ArrayList<>();

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

		}
		return matchingChords;
	}

	public List<NoteClass> getNotes(NoteClass tonic){
		List<NoteClass>  noteClass = new ArrayList<>();

		for(Solfege slfg : getSolfege()){
			noteClass.add(slfg.toNote(tonic));
		}

		return noteClass;
	}

	public RomanNumeral[] getRelativeChords() {
		return relativeChords;
	}

	public Solfege[] getSolfege() {
		return solfege;
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
