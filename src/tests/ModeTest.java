package tests;

import static java.lang.System.out;

import org.junit.Test;

import paul.wintz.music.keys.Mode;
import paul.wintz.music.notes.Note;
import paul.wintz.music.notes.Note.*;

public class ModeTest {

	@Test
	public void testGetNotes() throws Exception {
		for(Mode mode : Mode.values()){
			for (Base base : Base.values()) {
				for (Accidental accidental : Accidental.values()) {
					Note tonic = new Note(base, accidental);

					out.println("-------------\n"+ "Key: " + tonic.getName() + " " + mode.toString());

					out.println(mode.notesToString(tonic));
				}
			}
		}
	}
}
