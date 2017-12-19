package paul.wintz.musicmain;
import javax.sound.sampled.*;

public class Tone {

	public static void main(String[] args) throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);

		try(SourceDataLine line = AudioSystem.getSourceDataLine(af)){
			line.open(af, Note.SAMPLE_RATE);
			line.start();
			for  (Note n : Note.values()) {
				play(line, n, 200);
				play(line, Note.REST, 200);
			}
			line.drain();
		}

	}

	private static void play(SourceDataLine line, Note note, int ms) {
		ms = Math.min(ms, Note.SECONDS * 1000);
		int length = Note.SAMPLE_RATE * ms / 1000;
		int count = line.write(note.data(), 0, length);
		if(count == 0) {
			System.out.println("Nothing played");
		}
	}
}

enum Note {

	REST, A4, A_SHARP_4, B4, C4, CSHARP4, D4, D_SHARP4, E4, F4, F_SHARP4, G4, G_SHARP4, A5, G2, B2, D2, C3;
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
	public static final int SECONDS = 2;
	private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

	Note() {
		int n = this.ordinal();
		if (n > 0) {
			double exp = ((double) n - 1) / 12d;
			double f = 440d * Math.pow(2d, exp);
			for (int i = 0; i < sin.length; i++) {
				double period = SAMPLE_RATE / f;
				double angle = 2.0 * Math.PI * i / period;
				sin[i] = (byte)(Math.sin(angle) * 127f);
			}
		}
	}

	public byte[] data() {
		return sin;
	}
}