package paul.wintz.music.chords;

import static com.google.common.base.Preconditions.checkState;
import static paul.wintz.music.UnicodSymbol.*;
import static paul.wintz.music.chords.ChordQuality.*;
import static paul.wintz.music.chords.RomanNumeral.Type.*;
import static paul.wintz.music.keys.SolfegeEnum.*;

import java.util.*;

import paul.wintz.music.keys.*;

//A chord that is defined by (1) a specific quality, and (2) which degree of the scale the root is on.
public class RomanNumeral extends Chord {
	private static final Map<String, RomanNumeral> allRomanNumerals = new HashMap<>();
	private static Random random = new Random();

	private SolfegeEnum rootSolfege;
	private Type type;
	private List<RomanNumeral> possibleNextChords = new ArrayList<>();

	private final String representation;

	public RomanNumeral(SolfegeEnum rootSolfege, ChordQuality quality, Type type, String romanNumeral){
		this.rootSolfege = rootSolfege;
		this.quality = quality;
		this.representation = romanNumeral;
		this.type = type;
	}

	private void setPossibleNextChords(RomanNumeral... chords){
		for(RomanNumeral chord : chords){
			possibleNextChords.add(chord);
		}
	}

	public int getHalfStepsAboveTonic(){
		return rootSolfege.getHalfstepsAboveTonic();
	}

	public RomanNumeral getRandomNextChordInProgression(){
		checkState(!possibleNextChords.isEmpty(), "No next chord available for %s", this);

		RomanNumeral nextChord = possibleNextChords.get(random.nextInt(possibleNextChords.size()));
		for(int i = 0; i < 10; i++){
			if (nextChord.getRomanNumeralType() == DIATONIC) {
				break;
			}
			nextChord = possibleNextChords.get(random.nextInt(possibleNextChords.size()));
		}
		return nextChord;
	}

	enum Type {
		TONIC(-1),
		DIATONIC(0),
		CHROMATIC(3),
		BORROWED(2),
		SECONDARY_DOMINANT(4),
		HARMONIC_MINOR(0),
		NATURAL_MINOR(1);

		public final int precedence;

		private Type(int precedence) {
			this.precedence = precedence;
		}
	}

	public static class Major {
		public static final RomanNumeral[] MAJOR;

		static final RomanNumeral
		I, ii, iii, IV, V, vi, viiDim,	//Diatonic triads
		IM7, iim7, iiim7, IVM7, V7, vim7, viiHalfDim7, //Diatonic seventh chords in mode.
		iiHalfDim7Borrowed, flatIII, iv, flatVI,viiDim7; //Chromatic chords

		static {
			MAJOR = new RomanNumeral[] { I = new RomanNumeral(DO, MAJOR_TRIAD, TONIC, "I"),
					ii = new RomanNumeral(RE, MINOR_TRIAD, DIATONIC, "ii"),
					iii = new RomanNumeral(MI, MINOR_TRIAD, DIATONIC, "iii"),
					IV = new RomanNumeral(FA, MAJOR_TRIAD, DIATONIC, "IV"),
					V = new RomanNumeral(SOL,MAJOR_TRIAD, DIATONIC, "V"),
					vi = new RomanNumeral(LA, MINOR_TRIAD, DIATONIC, "vi"),
					viiDim = new RomanNumeral(TI, DIMINISHED_TRIAD, DIATONIC, "vii" + DIMINISHED),

					IM7 = new RomanNumeral(DO, MAJOR_7, DIATONIC, "I maj7"),
					iim7 = new RomanNumeral(RE, MINOR_7, DIATONIC, "ii min7"),
					iiim7 = new RomanNumeral(MI, MINOR_7, DIATONIC, "iii min7"),
					IVM7 = new RomanNumeral(FA, MAJOR_7, DIATONIC,"IV maj7"),
					V7 = new RomanNumeral(SOL,DOMINANT_7, DIATONIC,"V7"),
					vim7 = new RomanNumeral(LA, MINOR_7, DIATONIC, "vi min7"),
					viiHalfDim7 = new RomanNumeral(TI, HALF_DIMINISHED_7, DIATONIC,"vii" + HALF_DIMINISHED),

					//Borrowed from minor
					iiHalfDim7Borrowed = new RomanNumeral(RE, HALF_DIMINISHED_7, BORROWED, "ii" + HALF_DIMINISHED),
					flatIII = new RomanNumeral(ME, MAJOR_TRIAD, BORROWED, FLAT + "III"), //Flatted third
					iv = new RomanNumeral(FA, MINOR_TRIAD, BORROWED,"iv"), //minor fourth
					flatVI = new RomanNumeral(LE, MAJOR_TRIAD, BORROWED,FLAT + "VI"), //Flat sixth
					viiDim7 = new RomanNumeral(TI, DIMINISHED_7, BORROWED,"vii" + DIMINISHED + "7") //minor fourth
			};
			I.setPossibleNextChords(
					ii, iii, IV, V, vi, viiDim, IM7, iim7, iiim7, IVM7, V7, vim7, viiHalfDim7,
					iiHalfDim7Borrowed, flatIII, iv, viiDim7);
			IM7.setPossibleNextChords(
					ii, iii, IV, V, vi, viiDim, iim7, iiim7, IVM7, V7, vim7, viiHalfDim7,
					iiHalfDim7Borrowed, flatIII, iv, viiDim7);
			ii.setPossibleNextChords(iim7, V, V7);
			iim7.setPossibleNextChords(V, V7);
			iii.setPossibleNextChords(vi, vim7);
			iiim7.setPossibleNextChords(vi, vim7);
			IV.setPossibleNextChords(IVM7, V, V7, iv);
			IVM7.setPossibleNextChords(V, V7);
			V.setPossibleNextChords(I, IM7, vi, vim7, flatVI, V7);
			V7.setPossibleNextChords(I, IM7, vi, vim7, flatVI);
			vi.setPossibleNextChords(ii, iim7, vim7);
			vim7.setPossibleNextChords(ii, iim7);
			viiDim.setPossibleNextChords(I, IM7, viiHalfDim7);
			viiHalfDim7.setPossibleNextChords(I, IM7);

			iiHalfDim7Borrowed.setPossibleNextChords(flatIII, IV);
			flatIII.setPossibleNextChords(flatVI);
			iv.setPossibleNextChords(IV);
			flatVI.setPossibleNextChords(V);
			viiDim7.setPossibleNextChords(I, IM7);


			for(RomanNumeral chord : MAJOR){
				allRomanNumerals.put(getHash("MAJOR", chord), chord);
			}
		}

	}

	public static class Minor {
		public static final RomanNumeral[] MINOR;

		static final RomanNumeral
		i, iiDim, III, iv, v, VI, VII, //diatonic triads
		im7, iiHalfDim7, IIIM7, ivm7, vm7, VIM7, VII7, //diatonic seventh chords in mode.
		IIIAug, V, viiDim, V7, viiDim7,
		N; //Harmonic Minors

		static{

			MINOR = new RomanNumeral[] {
					//Define diatonic triads in mode.
					i = new RomanNumeral(DO, MINOR_TRIAD, TONIC, "i"),
							iiDim = new RomanNumeral(RE, DIMINISHED_TRIAD, DIATONIC, "ii" + DIMINISHED),
							III = new RomanNumeral(ME, MAJOR_TRIAD, NATURAL_MINOR, "III"),
							iv = new RomanNumeral(FA, MINOR_TRIAD, DIATONIC, "iv"),
							v = new RomanNumeral(SOL, MINOR_TRIAD, NATURAL_MINOR, "v"),
							VI = new RomanNumeral(LE, MAJOR_TRIAD, DIATONIC, "VI"),
							VII = new RomanNumeral(TE, MAJOR_TRIAD, NATURAL_MINOR, "VII"),

							//Define diatonic seventh chords in mode.
							im7 = new RomanNumeral(DO, MINOR_7, DIATONIC, "i min7"),
							iiHalfDim7 = new RomanNumeral(RE, HALF_DIMINISHED_7, DIATONIC, "ii" + HALF_DIMINISHED),
							IIIM7 = new RomanNumeral(ME, MAJOR_7, NATURAL_MINOR, "III maj7"),
							ivm7 = new RomanNumeral(FA, MINOR_7, DIATONIC, "iv min7"),
							vm7 = new RomanNumeral(SOL, MINOR_7, NATURAL_MINOR, "v min7"),
							VIM7 = new RomanNumeral(LE, MAJOR_7, DIATONIC, "VI maj7"),
							VII7 = new RomanNumeral(TE, DOMINANT_7, NATURAL_MINOR, "VII7"),

							//Harmonic Minor.
							IIIAug = new RomanNumeral(ME, AUGMENTED_TRIAD, HARMONIC_MINOR, "III+"),
							V = new RomanNumeral(SOL, MAJOR_TRIAD, HARMONIC_MINOR, "V"),
							viiDim = new RomanNumeral(TI, DIMINISHED_TRIAD, HARMONIC_MINOR, "vii" + DIMINISHED),
							V7 = new RomanNumeral(SOL, DOMINANT_7, HARMONIC_MINOR, "V7"),
							viiDim7 = new RomanNumeral(TI, DIMINISHED_7, HARMONIC_MINOR, "vii" + DIMINISHED + "7"),

							N = new RomanNumeral(RA, MAJOR_TRIAD, CHROMATIC, "N")
			};

			i.setPossibleNextChords(iiDim, III, iv, VI, VII, im7, iiHalfDim7, IIIM7, ivm7, vm7, VIM7, VII7, V, viiDim, V7, viiDim7);

			for	(RomanNumeral chord : MINOR){
				allRomanNumerals.put(getHash("MINOR", chord), chord);
			}
		}
	}

	public static Map<String, RomanNumeral> getAllRomanNumerals() {
		return allRomanNumerals;
	}

	private static String getHash(String mode, RomanNumeral chord) {
		return mode + chord.getHalfStepsAboveTonic() + chord.quality.name();
	}
	private static String getHash(Mode mode, int halfStepsAboveTonic, ChordQuality quality) {
		return mode.name() + halfStepsAboveTonic + quality.name();
	}

	//Returns null if no Roman numeral is found
	public static RomanNumeral makeRomanNumeral(Mode mode, int halfStepsAboveTonic, ChordQuality quality){
		return allRomanNumerals.get(getHash(mode, halfStepsAboveTonic, quality));
	}

	public Type getRomanNumeralType() {
		return type;
	}

	@Override
	public String toString() {
		return representation;
	}

}