package paul.wintz.musicmain;

import static java.lang.System.out;

import java.util.*;

import javax.sound.midi.*;

import paul.wintz.music.*;
import paul.wintz.music.chords.*;
import paul.wintz.music.keys.*;
import paul.wintz.music.notes.PitchClass;

public class ChordProgressionGeneratorMain {
	private static Random random = new Random();

	//converts midi data into paul.wintz.audio
	private static Synthesizer synth;

	//A collection of up to 128 banks. Each bank holds up to 128 instruments.
	private static Soundbank soundBank;

	//CLASSES
	//Specifies a bank and instrument. Used to change a channel's instrument.
	private static Patch patch;

	//Instrument
	private static Instrument instrument;
	//String getName()

	//Used to communicate to synthesizer in real time wth messages:
	private static Receiver synthRcvr;

	//Used to communicate directly to synth.
	private static MidiChannel chan[];

	/* The goal of this class is to take a chord a play it through the speakers
	 */
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, NullPointerException {

		synth = MidiSystem.getSynthesizer();
		synth.open();
		synthRcvr = synth.getReceiver();
		soundBank = synth.getDefaultSoundbank();
		chan = synth.getChannels();

		for(Instrument inst : synth.getLoadedInstruments()){
			out.println(inst.getName());
		}


		//		for(PitchClass root : PitchClass.values()){
		//			for(ChordQuality qual : ChordQuality.values()){
		//				AbsoluteChord chord = new AbsoluteChord(root, qual);
		for(AbsoluteChord chord : produceProgression()){
			System.out.println(chord.toString());
			playChord(chord);
			Thread.sleep(1000);
			stopNotes();
			Thread.sleep(300);
		}
		//			}
		//		}

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
		patch = new Patch(bank, program);
		instrument = soundBank.getInstrument(patch);
		out.println("instrument:  " + instrument.getName());
	}

	//TODO: move this somewhere it makes sense
	public static List<AbsoluteChord> produceProgression() {
		List<AbsoluteChord> progression = new ArrayList<>();

		String progressionString = "\n";
		Key key = Key.getKey(PitchClass.C, Mode.MAJOR);
		RomanNumeralChord romanNumeral = RomanNumeralChord.getRomanNumeral(Mode.MAJOR, 0, ChordQuality.MAJOR_TRIAD);
		AbsoluteChord absoluteChord = key.convertRomanNumeralToAbsoluteChord(romanNumeral);
		progression.add(absoluteChord);
		List<RomanNumeralChord> matchingChords = new ArrayList<>();
		List<Key> matchingKeys = new ArrayList<>();
		progressionString += key.toString() + ": ";
		for(int i = 0; i < 50; i++){
			//			for(RomanNumeralChord romanNumeral : RomanNumeralChord.getAllRomanNumerals().values()){

			matchingChords.clear();
			matchingKeys.clear();

			progressionString += "[" + romanNumeral.toString() + " | ";
			//String format: [I | C MAJOR: IV]
			for(Key keyToCheck: Key.getAllKeys()){

				RomanNumeralChord matchedChord = keyToCheck.convertAbsoluteChordToRomanNumeral(absoluteChord);
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
			progressionString += "\n" + key.toString() + ": " + romanNumeral.toString() + "] (" + absoluteChord.toString() + ")\n";

			for(int n = 0; n  < 10; n++){
				try{
					romanNumeral = romanNumeral.getRandomNextChordInProgression();
				} catch(NullPointerException e){
					continue;
				}
				absoluteChord = key.convertRomanNumeralToAbsoluteChord(romanNumeral);
				progression.add(absoluteChord);
				progressionString += "\t " + romanNumeral.toString() + " (" + absoluteChord.toString() + ")\n";
			}
		}
		System.out.println(progressionString);
		return progression;
	}


}
