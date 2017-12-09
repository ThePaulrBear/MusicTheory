package paul.wintz.music.chords;

import java.util.*;

import paul.wintz.music.UnicodSymbol;
import paul.wintz.music.keys.*;

//A chord that is defined by (1) a specific quality, and (2) which degree of the scale the root is on.
public class RomanNumeralChord extends Chord {
	private SolfegeEnum rootSolfege;
	private Type romanNumeralType;
	private List<RomanNumeralChord> possibleNextChords;

	private final String romanNumeral;
	private static final HashMap<String, RomanNumeralChord> allRomanNumerals = new HashMap<>();

	private static Random random = new Random();

	RomanNumeralChord(SolfegeEnum rootSolfege, ChordQuality quality, Type romanNumeralType, String romanNumeral){
		this.rootSolfege = rootSolfege;
		this.quality = quality;
		this.romanNumeral = romanNumeral;
		this.setRomanNumeralType(romanNumeralType);
		possibleNextChords = new ArrayList<>();
	}

	public void setPossibleNextChords(RomanNumeralChord...chords){
		for(RomanNumeralChord chord : chords){
			possibleNextChords.add(chord);
		}
	}

	public int getHalfStepsAboveTonic(){
		return rootSolfege.getHalfstepsAboveTonic();
	}

	//	public RelativeChord createChordWithoutSeventh(){
	//		RelativeChord newChord = new RelativeChord(mode, degree, isDiatonic);
	//		newChord.possibleNextChords = this.possibleNextChords;
	//		return newChord;
	//	}

	@Override
	public String toString() {
		return romanNumeral;
	}

	public RomanNumeralChord getRandomNextChordInProgression(){
		if(possibleNextChords.isEmpty()) throw new NullPointerException();
		RomanNumeralChord nextChord = possibleNextChords.get(random.nextInt(possibleNextChords.size()));
		for(int i = 0; i < 10; i++){
			if (nextChord.getRomanNumeralType() == RomanNumeralChord.Type.DIATONIC) {
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
		public static final RomanNumeralChord[] MAJOR;

		static final RomanNumeralChord
		I, ii, iii, IV, V, vi, viiDim,	//Diatonic triads
		IM7, iim7, iiim7, IVM7, V7, vim7, viiHalfDim7, //Diatonic seventh chords in mode.
		iiHalfDim7Borrowed, flatIII, iv, flatVI,viiDim7; //Chromatic chords

		static {
			MAJOR = new RomanNumeralChord[] { I = new RomanNumeralChord(SolfegeEnum.DO, ChordQuality.MAJOR_TRIAD, Type.TONIC, "I"),
					ii = new RomanNumeralChord(SolfegeEnum.RE, ChordQuality.MINOR_TRIAD, Type.DIATONIC, "ii"),
					iii = new RomanNumeralChord(SolfegeEnum.MI, ChordQuality.MINOR_TRIAD, Type.DIATONIC, "iii"),
					IV = new RomanNumeralChord(SolfegeEnum.FA, ChordQuality.MAJOR_TRIAD, Type.DIATONIC, "IV"),
					V = new RomanNumeralChord(SolfegeEnum.SOL,ChordQuality.MAJOR_TRIAD, Type.DIATONIC, "V"),
					vi = new RomanNumeralChord(SolfegeEnum.LA, ChordQuality.MINOR_TRIAD, Type.DIATONIC, "vi"),
					viiDim = new RomanNumeralChord(SolfegeEnum.TI, ChordQuality.DIMINISHED_TRIAD, Type.DIATONIC, "vii" + UnicodSymbol.DIMINISHED),

					IM7 = new RomanNumeralChord(SolfegeEnum.DO, ChordQuality.MAJOR_SEVENTH, Type.DIATONIC, "I maj7"),
					iim7 = new RomanNumeralChord(SolfegeEnum.RE, ChordQuality.MINOR_SEVENTH, Type.DIATONIC, "ii min7"),
					iiim7 = new RomanNumeralChord(SolfegeEnum.MI, ChordQuality.MINOR_SEVENTH, Type.DIATONIC, "iii min7"),
					IVM7 = new RomanNumeralChord(SolfegeEnum.FA, ChordQuality.MAJOR_SEVENTH, Type.DIATONIC,"IV maj7"),
					V7 = new RomanNumeralChord(SolfegeEnum.SOL,ChordQuality.DOMINANT_SEVENTH, Type.DIATONIC,"V7"),
					vim7 = new RomanNumeralChord(SolfegeEnum.LA, ChordQuality.MINOR_SEVENTH, Type.DIATONIC, "vi min7"),
					viiHalfDim7 = new RomanNumeralChord(SolfegeEnum.TI, ChordQuality.HALF_DIMINISHED_SEVENTH, Type.DIATONIC,"vii" + UnicodSymbol.HALF_DIMINISHED),


					//Borrowed from minor
					iiHalfDim7Borrowed = new RomanNumeralChord(SolfegeEnum.RE, ChordQuality.HALF_DIMINISHED_SEVENTH, Type.BORROWED, "ii" + UnicodSymbol.HALF_DIMINISHED),
					flatIII = new RomanNumeralChord(SolfegeEnum.ME, ChordQuality.MAJOR_TRIAD, Type.BORROWED, UnicodSymbol.FLAT + "III"), //Flatted third
					iv = new RomanNumeralChord(SolfegeEnum.FA, ChordQuality.MINOR_TRIAD, Type.BORROWED,"iv"), //minor fourth
					flatVI = new RomanNumeralChord(SolfegeEnum.LE, ChordQuality.MAJOR_TRIAD, Type.BORROWED,UnicodSymbol.FLAT + "VI"), //Flat sixth
					viiDim7 = new RomanNumeralChord(SolfegeEnum.TI, ChordQuality.DIMINISHED_SEVENTH, Type.BORROWED,"vii" + UnicodSymbol.DIMINISHED + "7") //minor fourth
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


			for(RomanNumeralChord chord : MAJOR){
				allRomanNumerals.put(getHashCodeKey("MAJOR", chord), chord);
			}
		}

	}

	public static class Minor {
		public static final RomanNumeralChord[] MINOR;

		static final RomanNumeralChord
		i, iiDim, III, iv, v, VI, VII, //diatonic triads
		im7, iiHalfDim7, IIIM7, ivm7, vm7, VIM7, VII7, //diatonic seventh chords in mode.
		IIIAug, V, viiDim, V7, viiDim7,
		N; //Harmonic Minors

		static{

			MINOR = new RomanNumeralChord[] {
					//Define diatonic triads in mode.
					i = new RomanNumeralChord(SolfegeEnum.DO, ChordQuality.MINOR_TRIAD, Type.TONIC, "i"),
							iiDim = new RomanNumeralChord(SolfegeEnum.RE, ChordQuality.DIMINISHED_TRIAD, Type.DIATONIC, "ii" + UnicodSymbol.DIMINISHED),
							III = new RomanNumeralChord(SolfegeEnum.ME, ChordQuality.MAJOR_TRIAD, Type.NATURAL_MINOR, "III"),
							iv = new RomanNumeralChord(SolfegeEnum.FA, ChordQuality.MINOR_TRIAD, Type.DIATONIC, "iv"),
							v = new RomanNumeralChord(SolfegeEnum.SOL, ChordQuality.MINOR_TRIAD, Type.NATURAL_MINOR, "v"),
							VI = new RomanNumeralChord(SolfegeEnum.LE, ChordQuality.MAJOR_TRIAD, Type.DIATONIC, "VI"),
							VII = new RomanNumeralChord(SolfegeEnum.TE, ChordQuality.MAJOR_TRIAD, Type.NATURAL_MINOR, "VII"),

							//Define diatonic seventh chords in mode.
							im7 = new RomanNumeralChord(SolfegeEnum.DO, ChordQuality.MINOR_SEVENTH, Type.DIATONIC, "i min7"),
							iiHalfDim7 = new RomanNumeralChord(SolfegeEnum.RE, ChordQuality.HALF_DIMINISHED_SEVENTH, Type.DIATONIC, "ii" + UnicodSymbol.HALF_DIMINISHED),
							IIIM7 = new RomanNumeralChord(SolfegeEnum.ME, ChordQuality.MAJOR_SEVENTH, Type.NATURAL_MINOR, "III maj7"),
							ivm7 = new RomanNumeralChord(SolfegeEnum.FA, ChordQuality.MINOR_SEVENTH, Type.DIATONIC, "iv min7"),
							vm7 = new RomanNumeralChord(SolfegeEnum.SOL, ChordQuality.MINOR_SEVENTH, Type.NATURAL_MINOR, "v min7"),
							VIM7 = new RomanNumeralChord(SolfegeEnum.LE, ChordQuality.MAJOR_SEVENTH, Type.DIATONIC, "VI maj7"),
							VII7 = new RomanNumeralChord(SolfegeEnum.TE, ChordQuality.DOMINANT_SEVENTH, Type.NATURAL_MINOR, "VII7"),

							//Harmonic Minor.
							IIIAug = new RomanNumeralChord(SolfegeEnum.ME, ChordQuality.AUGMENTED_TRIAD, Type.HARMONIC_MINOR, "III+"),
							V = new RomanNumeralChord(SolfegeEnum.SOL, ChordQuality.MAJOR_TRIAD, Type.HARMONIC_MINOR, "V"),
							viiDim = new RomanNumeralChord(SolfegeEnum.TI, ChordQuality.DIMINISHED_TRIAD, Type.HARMONIC_MINOR, "vii" + UnicodSymbol.DIMINISHED),
							V7 = new RomanNumeralChord(SolfegeEnum.SOL, ChordQuality.DOMINANT_SEVENTH, Type.HARMONIC_MINOR, "V7"),
							viiDim7 = new RomanNumeralChord(SolfegeEnum.TI, ChordQuality.DIMINISHED_SEVENTH, Type.HARMONIC_MINOR, "vii" + UnicodSymbol.DIMINISHED + "7"),

							N = new RomanNumeralChord(SolfegeEnum.RA, ChordQuality.MAJOR_TRIAD, Type.CHROMATIC, "N")
			};

			i.setPossibleNextChords(iiDim, III, iv, VI, VII, im7, iiHalfDim7, IIIM7, ivm7, vm7, VIM7, VII7, V, viiDim, V7, viiDim7);

			for	(RomanNumeralChord chord : MINOR){
				allRomanNumerals.put(getHashCodeKey("MINOR", chord), chord);
			}
		}

	}

	public static Map<String, RomanNumeralChord> getAllRomanNumerals() {
		return allRomanNumerals;
	}

	private static String getHashCodeKey(String mode, RomanNumeralChord chord) {
		return mode + chord.getHalfStepsAboveTonic() + chord.quality.name();
	}
	private static String getHashCodeKey(Mode mode, int halfStepsAboveTonic, ChordQuality quality) {
		return mode.name() + halfStepsAboveTonic + quality.name();
	}

	//Returns null if no Roman numeral is found
	public static RomanNumeralChord getRomanNumeral(Mode mode, int halfStepsAboveTonic, ChordQuality quality){
		return allRomanNumerals.get(getHashCodeKey(mode, halfStepsAboveTonic, quality));
	}

	public Type getRomanNumeralType() {
		return romanNumeralType;
	}

	public void setRomanNumeralType(Type romanNumeralType) {
		this.romanNumeralType = romanNumeralType;
	}





}