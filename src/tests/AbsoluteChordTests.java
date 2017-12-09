package tests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import paul.wintz.music.*;
import paul.wintz.music.chords.*;
import paul.wintz.music.notes.PitchClass;

public class AbsoluteChordTests  {

	public static String newLine = System.getProperty("line.separator");

	@Test
	public void testEquals() throws Exception{
		for(PitchClass root : PitchClass.values()){
			for(ChordQuality quality : ChordQuality.values()){
				AbsoluteChord a = new AbsoluteChord(root, quality);
				AbsoluteChord b = new AbsoluteChord(a.getRoot(), a.getQuality());
				assertEquals("Equals() failed", a, b);
			}
		}
	}

	@Test
	public void testEquals2() throws Exception{
		for(PitchClass root : PitchClass.values()){
			for(ChordQuality quality : ChordQuality.values()){
				AbsoluteChord a = new AbsoluteChord(root, quality);
				if(!a.isValid()) {
					continue;
				}
				AbsoluteChord b = new AbsoluteChord(a.getNotes());

				assertEquals("Equals() failed.\n" +a.toString() + ": " + a.pitchClassesToString()
				+"\n" + b.toString() + ": " + b.pitchClassesToString() + "\n", a, b);
			}
		}
	}

	@Test
	public void testNewChordFromNotes() throws Exception {
		for(PitchClass root : PitchClass.values()){
			for(ChordQuality quality : ChordQuality.values()){

				AbsoluteChord chord = new AbsoluteChord(root, quality);
				if(!chord.isValid()) {
					continue;
				}
				AbsoluteChord chordFromNotes = new AbsoluteChord(chord.getNotes());

				assertEquals(compareNotesToString(chord, chordFromNotes), chord.getRoot(), chordFromNotes.getRoot());
				assertEquals(compareNotesToString(chord, chordFromNotes), chord.getQuality(), chordFromNotes.getQuality());
			}
		}
	}


	/**
	 * @param chord
	 * @param chordFromNotes
	 * @return
	 */
	private String compareNotesToString(AbsoluteChord chord, AbsoluteChord chordFromNotes) {
		return "Notes in expected: " + chord.pitchClassesToString() + "\nNotes in actual: " + chordFromNotes.pitchClassesToString();
	}
}
