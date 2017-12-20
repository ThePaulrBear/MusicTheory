package tests;

import static java.lang.System.out;

import org.junit.Test;

import paul.wintz.music.keys.Mode;
import paul.wintz.music.notes.*;
import paul.wintz.music.notes.NoteClass.*;

public class ModeTest {

	@Test
	public void testGetNotes() throws Exception {
		for(Mode mode : Mode.values()){
			for (NoteLetter noteLetter : NoteLetter.values()) {
				for (Accidental accidental : Accidental.values()) {
					NoteClass tonic = new NoteClass(noteLetter, accidental);

					out.println("-------------\n"+ "Key: " + tonic.getName() + " " + mode.toString());

					out.println(mode.notesToString(tonic));
				}
			}
		}
	}
}
