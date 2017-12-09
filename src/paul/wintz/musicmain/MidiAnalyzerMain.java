package paul.wintz.musicmain;

import java.io.*;
import java.util.*;

import javax.sound.midi.*;

import paul.wintz.music.*;
import paul.wintz.music.chords.Progression;
import paul.wintz.music.notes.PitchClass;

public class MidiAnalyzerMain {

	private static final String AVE_MARIA_BACH = "midi/Ave-Maria-Bach.mid";
	private static final String SHEEP_MAY_SAFELY_GAZE = "midi/bach/Sheep_may_safely_gaze.mid";
	private static final String BEETHOVEN_FIFTH = "midi/5th-Symphony-Part-1.mid";
	private static final String BEETHOVEN_NINTH = "midi/9beethoven4mvto.mid";
	private static final String PICTURES_AT_EXPO = "midi/Baba-Yaga's-Hut-On-Fowl's-Legs-Great-Gate-Of-Kiev.mid";
	private static final String ADAGIO = "midi/Adagio-in-B.mid";
	private static final String RUGGED_CROSS = "midi/hymns/The_Old_Rugged_Cross.mid";
	private static final String BACH_FUGUE = "midi/bach/bach_fugue.mid";
	private static final String BACH_TOCCATA_AND_FUGUE = "midi/bach/BACH_Johann_Sebastian_-_Fugue-_Toccata_for_church_organ.mid";
	private static final String BACH_INVENTION = "midi/bach/Bach.mid";
	private static final String ADAGIO_FOR_STRINGS = "midi/Adagio-For-Strings-Opus-11.mid";
	private static final String WONDERFUL_WORLD = "midi/ARMSTRONG.What wonderful world 2 K.MID";

	private static String songFile = AVE_MARIA_BACH;
	private static int millisPerFrame = 25;

	private static Synthesizer synth;

	private static List<PitchClass> notes = new ArrayList<>();
	private static List<Integer> volumes = new ArrayList<>();

	public static void main(String[] args) throws Exception {

		Sequencer sequencer = setupSequencer();
		Progression progression = new Progression();

		while(sequencer.isRunning()){
			Thread.sleep(millisPerFrame);
			notes.clear();
			volumes.clear();

			readNotesAndVolumes();

			progression.add(notes);

			//Determine the key
		}

		sequencer.close();
		synth.close();
	}


	private static Sequencer setupSequencer() throws MidiUnavailableException, InvalidMidiDataException, IOException {
		synth = MidiSystem.getSynthesizer();
		synth.getDefaultSoundbank();
		synth.open();

		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();

		Receiver receiver = synth.getReceiver();

		Sequence sequence = MidiSystem.getSequence(new File(songFile));
		sequencer.setSequence(sequence);

		Transmitter transmitter = sequencer.getTransmitter();
		transmitter.setReceiver(receiver);

		sequencer.start();
		return sequencer;
	}


	/**
	 *
	 */
	private static void readNotesAndVolumes() {
		for(VoiceStatus voice: synth.getVoiceStatus()){
			if(voice.active && !isPercussion(voice)) {

				PitchClass currentNote = PitchClass.getFromMidiNumber(voice.note);
				int currentIndex = notes.lastIndexOf(currentNote);
				if(currentIndex != -1){
					if(voice.volume > volumes.get(currentIndex)){
						volumes.remove(currentIndex);
						notes.remove(currentIndex);
					} else {
						continue;
					}
				}

				int indexToInsert = 0;
				for(int i = notes.size() - 1; i >= 0; i--){

					//check each element, starting at the end, of volumes. When the value checked is greater than the
					// current value, add a new  element after it.
					if(volumes.get(i) > voice.volume) {
						indexToInsert = i + 1;
						break;
					}
				}
				volumes.add(indexToInsert, voice.volume);
				notes.add(indexToInsert, PitchClass.getFromMidiNumber(voice.note));

			}
		}
	}

	/**Checks if the current voice is in the channel (usually?) reserved for percussion.
	 *
	 * @param voice
	 * @return
	 */
	private static boolean isPercussion(VoiceStatus voice) {
		return voice.channel == 9;
	}


}
