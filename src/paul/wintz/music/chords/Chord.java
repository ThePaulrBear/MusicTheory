package paul.wintz.music.chords;

public abstract class Chord {
	public ChordQuality quality;

	public boolean isSeventhChord(){
		return quality.isSeventhChord();
	}

	public static boolean isSameQuality(Chord chord1, Chord chord2){
		return (chord1.quality == chord2.quality);
	}

	public ChordQuality getQuality() {
		return quality;
	}
}
