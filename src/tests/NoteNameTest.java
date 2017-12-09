package tests;

import static java.lang.System.out;
import static org.junit.Assert.*;

import paul.wintz.music.*;
import paul.wintz.music.chords.AbsoluteChord;
import paul.wintz.music.intervals.IntervalEnum;
import paul.wintz.music.notes.Note;
import paul.wintz.music.notes.Note.*;

import org.junit.*;


public class NoteNameTest {

//	@BeforeClass
//	public static void beforeTesting() {}
//	@AfterClass
//	public static void afterTesting() {}
//	@Before
//	public void setUp() throws Exception {}
//	@After
//	public void tearDown() throws Exception {}


	@Test
	public void testNoteName() throws Exception {
		Note name = new Note(Base.D, Accidental.SHARP);
		assertEquals("Names don't match", name.getName(), "D" + UnicodSymbol.SHARP);
	}
	
	@Test
	public void testGetNoteAtInterval() throws Exception {
		for(IntervalEnum intervalEnum : IntervalEnum.values()){
			for(Base base : Base.values()){
				for(Accidental acc : Accidental.values()){
					Note note = new Note(base, acc);
					Note second = Note.getNoteAtInterval(note, intervalEnum);
					
					String errorMsg = "Note: " + note.getName() +
							"\nInterval: " + intervalEnum.name() +
							"\nSecond note: " + second.getName()
							;
								
					assertEquals("Wrong note number.\n" + errorMsg, (note.getNoteNumber() + intervalEnum.getHalfSteps()) % 12, second.getNoteNumber());
					
					if(second.isDefaultName()){
						out.println("Invalid note, reverted to default note for pitch class\n" + errorMsg);
					} else {
						assertEquals("Wrong base.\n" + errorMsg, (note.getBase().ordinal() + intervalEnum.getScaleSteps()) % Base.values().length, second.getBase().ordinal());
					}
				}
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
