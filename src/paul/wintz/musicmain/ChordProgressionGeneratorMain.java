package paul.wintz.musicmain;

import static java.lang.System.out;

import java.util.*;

import javax.sound.midi.*;

import paul.wintz.music.chords.*;
import paul.wintz.music.keys.*;
import paul.wintz.music.notes.PitchClass;

public class ChordProgressionGeneratorMain {
	private static Random random = new Random();

	//A collection of up to 128 banks. Each bank holds up to 128 instruments.
	private static Soundbank soundBank;

	//Used to communicate to synthesizer in real time with messages:
	private static Receiver synthRcvr;

	//Used to communicate directly to synth.
	private static MidiChannel chan[];

	public static void main(String[] args) throws MidiUnavailableException, InterruptedException {

		//converts midi data into paul.wintz.audio
		try(Synthesizer synth = MidiSystem.getSynthesizer()){
			synth.open();
			synthRcvr = synth.getReceiver();
			soundBank = synth.getDefaultSoundbank();
			chan = synth.getChannels();

			produceProgression();
		}

	}

	private static ShortMessage tellSythToStartPlayingNote() throws InvalidMidiDataException, MidiUnavailableException{
		ShortMessage myMsg = new ShortMessage();
		// Play the note Middle C (60) moderately loud
		// (velocity = 93)on channel 4 (zero-based).

		int channel = 4; //zero-based
		int noteNumber = 60;
		int velocity = 93;
		myMsg.setMessage(ShortMessage.NOTE_ON, channel, noteNumber, velocity);

		synthRcvr.send(myMsg, -1); // -1 means no time stamp

		return myMsg;
	}

	private static void startNote(){
		// Check for null; maybe not all 16 channels exist.
		if (chan[4] != null) {
			chan[4].noteOn(60, 93);
			chan[4].noteOn(63, 93);
			chan[4].noteOn(66, 93);

		}
	}

	private static void stopNotes(){
		chan[0].allNotesOff();
	}

	private static void playChord(AbsoluteChord chord){
		for(PitchClass note : chord.getNotes()){
			chan[0].noteOn(60 + note.getNoteNumber(), 100);
		}
	}

	private static void selectInstrument(int bank, int program) {
		//Specifies a bank and instrument. Used to change a channel's instrument.
		Patch patch = new Patch(bank, program);
		Instrument instrument = soundBank.getInstrument(patch);
		out.println("instrument:  " + instrument.getName());
	}

	public static void produceProgression() throws InterruptedException {

		Key key = Key.getKey(PitchClass.C, Mode.MAJOR);
		RomanNumeral romanNumeral = RomanNumeral.makeRomanNumeral(Mode.MAJOR, 0, ChordQuality.MAJOR_TRIAD);
		AbsoluteChord absoluteChord = key.absoluteChordFromRomanNumeral(romanNumeral);

		List<RomanNumeral> matchingChords = new ArrayList<>();
		List<Key> matchingKeys = new ArrayList<>();
		System.out.println(key + ": ");
		for(int i = 0; i < 50; i++){

			matchingChords.clear();
			matchingKeys.clear();

			System.out.println("[" + romanNumeral + " | ");
			//String format: [I | C MAJOR: IV]
			for(Key keyToCheck: Key.getAllKeys()){

				RomanNumeral matchedChord = keyToCheck.romanNumeralFromAbsoluteChord(absoluteChord);
				if(matchedChord == null) {
					continue;
				}

				matchingChords.add(matchedChord);
				matchingKeys.add(keyToCheck);

			}
			int index = 0;
			for(int n = 0; n < 10; n++){
				index = random.nextInt(matchingChords.size());
				if(key != matchingKeys.get(index)) {
					break;
				}
			}
			romanNumeral = matchingChords.get(index);
			key = matchingKeys.get(index);
			System.out.println(key + ": " + romanNumeral + "] (" + absoluteChord + ")");

			for(int n = 0; n  < 10; n++){
				try{
					romanNumeral = romanNumeral.getRandomNextChordInProgression();
				} catch(NullPointerException e){
					continue;
				}
				absoluteChord = key.absoluteChordFromRomanNumeral(romanNumeral);
				System.out.println("\t " + romanNumeral + " (" + absoluteChord + ")");

				playChord(absoluteChord);
				Thread.sleep(1000);
				stopNotes();
				Thread.sleep(300);
			}

		}
	}


}
